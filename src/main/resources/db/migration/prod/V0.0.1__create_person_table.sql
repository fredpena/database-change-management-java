CREATE TABLE tenant
(
    tenant_id   BIGSERIAL           NOT NULL,
    tenant_name varchar(100) UNIQUE NOT NULL,
    CONSTRAINT tenant_pkey PRIMARY KEY (tenant_id)
);
CREATE INDEX idxdcxf3ksi0gyn1tieeq0id96lm ON tenant USING btree (tenant_name);

CREATE TABLE person
(
    id         BIGSERIAL           NOT NULL,
    tenant_id  int8                NOT NULL,
    first_name VARCHAR(100)        NOT NULL,
    last_name  VARCHAR(100)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,

    CONSTRAINT person_pkey PRIMARY KEY (id)
);