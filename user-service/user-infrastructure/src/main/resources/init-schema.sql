DROP SCHEMA IF EXISTS "user" CASCADE;
CREATE SCHEMA "user";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');


DROP TABLE IF EXISTS "user".user CASCADE;
CREATE TABLE "user".user
(
    id bigint NOT NULL,
    email varchar(255),
    password varchar(255),
    customer_id bigint UNIQUE,
    role_id bigint NOT NULL ,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS role_type;
CREATE TYPE role_type AS ENUM ('ADMIN', 'CUSTOMER');


DROP TABLE IF EXISTS "user".role CASCADE;
CREATE TABLE "user".role
(
    id bigint NOT NULL,
    role_type role_type NOT NULL,
    permissions varchar(255),
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

ALTER TABLE "user".user
    ADD CONSTRAINT "FK_ROLE_ID" FOREIGN KEY (role_id)
        REFERENCES "user".role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;
