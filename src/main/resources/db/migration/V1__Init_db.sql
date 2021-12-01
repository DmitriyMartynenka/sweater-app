create table if not exists usr
(
	id int8 not null
		constraint usr_pkey
			primary key,
	activation_code varchar(255),
	active boolean not null,
	email varchar(255),
	password varchar(255) not null,
	username varchar(255) not null
);

alter table usr owner to postgres;

create table if not exists message
(
	id int8 not null
		constraint message_pkey
			primary key,
	filename varchar(255),
	tag varchar(255),
	text varchar(2048),
	user_id bigint
		constraint message_user_fk
			references usr
);

alter table message owner to postgres;

create table if not exists user_role
(
	user_id int8 not null
		constraint user_role_user_fk
			references usr,
	roles varchar(255)
);

alter table user_role owner to postgres;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;