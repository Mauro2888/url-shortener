set schema 'short_url';
-- Create the urls table
CREATE table if not exists urls
(
    id           UUID         not null,
    original_url varchar(255) not null,
    short_url    varchar(255) not null,
    hashcode     varchar(255) not null,
    created_at   timestamptz,
    updated_at   timestamptz  not null,
    version      int8         not null,
    primary key (id)

);