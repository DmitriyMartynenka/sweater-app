package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailSender mailSender;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("someEmail");
        boolean isCreated = userService.addUser(user);
        Assertions.assertTrue(isCreated);
        Assertions.assertTrue(user.isActive());
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1)).
                send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to Sweater. Please visit next link:"));
    }

    @Test
    void userFailTest() {
        User user = new User();
        user.setUsername("someName");
        Mockito.doReturn(user).when(userRepo).findByUsername("someName");
        boolean isCreated = userService.addUser(user);
        Assertions.assertFalse(isCreated);
        Mockito.verify(userRepo, Mockito.times(0)).save(user);
        Mockito.verify(mailSender, Mockito.times(0)).
                send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    void activateUser() {
        User user = new User();
        user.setActivationCode("someCode");
        Mockito.doReturn(user).when(userRepo).findByActivationCode("someCode");
        boolean isActivated = userService.activateUser("someCode");
        Mockito.verify(userRepo, Mockito.times(1)).findByActivationCode("someCode");
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Assertions.assertNull(user.getActivationCode());
        Assertions.assertTrue(isActivated);
    }

    @Test
    void activateFailTest() {
        boolean isActivated = userService.activateUser("someCode");
        Assertions.assertFalse(isActivated);
        Mockito.verify(userRepo, Mockito.times(1)).findByActivationCode("someCode");
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}