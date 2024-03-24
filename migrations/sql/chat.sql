create table if not exists chat
(
    chat_id bigint not null primary key,
    name varchar(255) not null
);
