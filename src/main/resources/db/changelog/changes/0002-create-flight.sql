-- liquibase formatted sql
-- changeset gatis:2
CREATE TABLE FLIGHT
(
    id              SERIAL PRIMARY KEY,
    from_airport_id BIGINT    NOT NULL,
    to_airport_id   BIGINT    NOT NULL,
    carrier         VARCHAR   NOT NULL,
    departure_time  TIMESTAMP NOT NULL,
    arrival_time    TIMESTAMP NOT NULL,
    FOREIGN KEY (from_airport_id) REFERENCES AIRPORT (id),
    FOREIGN KEY (to_airport_id) REFERENCES AIRPORT (id)
);