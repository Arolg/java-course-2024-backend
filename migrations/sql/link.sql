create table if not exists link
(
    id bigserial primary key,
    url varchar(255) unique,
    updated_at timestamp with time zone
);
