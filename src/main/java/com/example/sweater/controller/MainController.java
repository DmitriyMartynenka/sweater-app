package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Map<String, Object> model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        model.put("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam(name = "file", required = false) MultipartFile file
    ) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            addFile(message, file);
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    private void addFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File fileDir = new File(uploadPath);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            String uuidFilename = UUID.randomUUID().toString();
            String resultFilename = uuidFilename + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{currentUserId}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable(value = "currentUserId") User user,
                               Model model,
                               @RequestParam(required = false) Message message) {
        Set<Message> messages = user.getMessages();
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("message", message);
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", user.equals(currentUser));
        return "userMessages";
    }

    @PostMapping("/user-messages/{currentUserId}")
    public String editMessage(@AuthenticationPrincipal User currentUser,
                              @PathVariable(value = "currentUserId") User user,
                              @RequestParam(value = "messageId") Message message,
                              @RequestParam String text,
                              @RequestParam String tag,
                              @RequestParam MultipartFile file) throws IOException {
        if (user.equals(currentUser)) {
            if (StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (StringUtils.isEmpty(text)) {
                message.setTag(tag);
            }
            addFile(message, file);
            messageRepo.save(message);
        }
        return "redirect:/user-messages/" + user.getId();
    }
}