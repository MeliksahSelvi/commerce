DROP SCHEMA IF EXISTS "notification" CASCADE;
CREATE SCHEMA "notification";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');

DROP TYPE IF EXISTS notification_status;
CREATE TYPE notification_status AS ENUM ('APPROVED', 'SHIPPED','DELIVERED','CANCELLED');

DROP TABLE IF EXISTS "notification".order_notification CASCADE;
CREATE TABLE "notification".order_notification
(
    id bigint NOT NULL,
    order_id bigint UNIQUE NOT NULL,
    customer_id bigint NOT NULL,
    notification_status notification_status NOT NULL,
    message varchar(4000),
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT order_notification_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "notification".member_verification CASCADE;
CREATE TABLE "notification".member_verification
(
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    url varchar(255) NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT member_verification_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "notification".reset_password_verification CASCADE;
CREATE TABLE "notification".reset_password_verification
(
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    url varchar(255) NOT NULL,
    expiration_date TIMESTAMP NOT NULL ,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT reset_password_verification_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "notification".two_factor_verification CASCADE;
CREATE TABLE "notification".two_factor_verification
(
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    code varchar(10) NOT NULL,
    expiration_date TIMESTAMP NOT NULL ,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT two_factor_verification_pkey PRIMARY KEY (id)
);

