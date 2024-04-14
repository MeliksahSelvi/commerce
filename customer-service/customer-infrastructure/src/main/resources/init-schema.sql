DROP SCHEMA IF EXISTS "customer" CASCADE;
CREATE SCHEMA "customer";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');

DROP TABLE IF EXISTS "customer".customer CASCADE;
CREATE TABLE "customer".customer
(
    id bigint NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    identity_no varchar(255) UNIQUE NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);