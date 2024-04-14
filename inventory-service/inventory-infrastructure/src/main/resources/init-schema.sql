DROP SCHEMA IF EXISTS "inventory" CASCADE;
CREATE SCHEMA "inventory";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');

DROP TABLE IF EXISTS "inventory".product CASCADE;
CREATE TABLE "inventory".product
(
    id bigint NOT NULL,
    name varchar(255) NOT NULL,
    price numeric(15,2) NOT NULL,
    quantity integer NOT NULL,
    availability boolean NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED','FAILED');


DROP TABLE IF EXISTS "inventory".order_outbox CASCADE;
CREATE TABLE "inventory".order_outbox
(
    id bigint NOT NULL,
    saga_id uuid NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT order_outbox_pkey PRIMARY KEY (id)
)
