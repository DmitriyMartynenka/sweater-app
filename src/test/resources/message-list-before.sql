delete from message;

insert into message (id, text, tag, user_id)
values (1, 'first','new', 1),
       (2, 'second','some_tag', 1),
       (3, 'third','new', 1),
       (4, 'fourth','another_tag', 1);

alter sequence hibernate_sequence restart with 10;