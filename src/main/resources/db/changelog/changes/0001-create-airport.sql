-- liquibase formatted sql
-- changeset gatis:1
CREATE TABLE AIRPORT
(
    id      SERIAL PRIMARY KEY,
    country VARCHAR NOT NULL,
    city    VARCHAR NOT NULL,
    airport VARCHAR NOT NULL
);