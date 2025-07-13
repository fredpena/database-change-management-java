CREATE TABLE person
(
    id         BIGSERIAL           NOT NULL,
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);