create table user_subscriptions
(
    channel_id    int8 not null
        constraint user_subscriptions_fk
            references usr,
    subscriber_id int8 not null
        constraint user_subscriptions__fk
            references usr,
    constraint user_subscriptions_pk
        primary key (channel_id, subscriber_id)
);

alter table user_subscriptions
    owner to postgres;