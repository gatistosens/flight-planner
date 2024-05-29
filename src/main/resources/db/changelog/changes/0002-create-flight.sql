-- liquibase formated sql
--changeset gatis:2

create table FLIGHT
(
    id              serial primary key,
    from_airport_id bigint    not null,
    to_airport_id   bigint    not null,
    carrier         varchar   not null,
    departure_time  timestamp not null,
    arrival_time    timestamp not null
)