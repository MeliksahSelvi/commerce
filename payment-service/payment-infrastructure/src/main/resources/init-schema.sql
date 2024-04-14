DROP SCHEMA IF EXISTS "payment" CASCADE;
CREATE SCHEMA "payment";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');

DROP TYPE IF EXISTS payment_status;
CREATE TYPE payment_status AS ENUM ('COMPLETED', 'CANCELLED', 'FAILED');

DROP TABLE IF EXISTS "payment".payment CASCADE;
CREATE TABLE "payment".payment
(
    id bigint NOT NULL,
    customer_id bigint NOT NULL,
    order_id bigint NOT NULL,
    cost numeric(15,2) NOT NULL,
    payment_status payment_status NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT payment_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS currency_type;
CREATE TYPE currency_type AS ENUM ('TL', 'EURO', 'DOLLAR');

DROP TABLE IF EXISTS "payment".account CASCADE;
CREATE TABLE "payment".account
(
    id bigint NOT NULL,
    customer_id bigint NOT NULL,
    current_balance numeric(15,2) NOT NULL,
    currency_type currency_type NOT NULL,
    iban_no varchar(26) NOT NULL,
    cancel_date TIMESTAMP,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT account_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS activity_type;
CREATE TYPE activity_type AS ENUM ('WITHDRAW', 'DEPOSIT', 'SEND','GET','SPEND','PAYBACK','FAIL');


DROP TABLE IF EXISTS "payment".account_activity CASCADE;
CREATE TABLE "payment".account_activity
(
    id bigint NOT NULL,
    account_id bigint NOT NULL,
    cost numeric(15,2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    current_balance numeric(15,2) NOT NULL,
    activity_type activity_type NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT account_activity_pkey PRIMARY KEY (id)
);

ALTER TABLE "payment".account_activity
    ADD CONSTRAINT "FK_ACCOUNT_ID" FOREIGN KEY (account_id)
        REFERENCES "payment".account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TABLE IF EXISTS "payment".order_outbox CASCADE;
CREATE TABLE "payment".order_outbox
(
    id bigint NOT NULL,
    saga_id uuid NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    payment_status payment_status NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT order_outbox_pkey PRIMARY KEY (id)
);
