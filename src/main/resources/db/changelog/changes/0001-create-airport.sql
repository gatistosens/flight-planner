-- liquibase formated sql
--changeset gatis:1

create table AIRPORT
(
    id serial primary key,
    country varchar not null ,
    city varchar not null ,
    airport varchar not null

)