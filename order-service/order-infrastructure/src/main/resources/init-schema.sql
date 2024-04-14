DROP SCHEMA IF EXISTS "order" CASCADE;
CREATE SCHEMA "order";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('CHECKING','PENDING', 'PAID', 'APPROVED', 'CANCELLED', 'CANCELLING');

DROP TABLE IF EXISTS "order".orders CASCADE;
CREATE TABLE "order".orders
(
    id bigint NOT NULL,
    customer_id bigint NOT NULL,
    cost numeric(15,2) NOT NULL,
    order_status order_status NOT NULL,
    failure_messages varchar(4000),
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "order".order_item CASCADE;
CREATE TABLE "order".order_item
(
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    price numeric(15,2) NOT NULL,
    quantity integer NOT NULL,
    total_price numeric(15,2) NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT order_item_pkey PRIMARY KEY (id)
);

ALTER TABLE "order".order_item
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
        REFERENCES "order".orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS "order".address CASCADE;
CREATE TABLE "order".address
(
    id bigint NOT NULL,
    order_id bigint UNIQUE NOT NULL,
    city varchar(50) NOT NULL,
    county varchar(50) NOT NULL,
    neighborhood varchar(50) NOT NULL,
    street varchar(100) NOT NULL,
    postal_code varchar(5),
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

ALTER TABLE "order".address
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
        REFERENCES "order".orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;

DROP TYPE IF EXISTS saga_status;
CREATE TYPE saga_status AS ENUM ('CHECKING', 'PAYING', 'PROCESSING', 'SUCCEEDED', 'CANCELLING', 'CANCELLED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TABLE IF EXISTS "order".payment_outbox CASCADE;
CREATE TABLE "order".payment_outbox
(
    id bigint NOT NULL,
    saga_id uuid NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    order_status order_status NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT payment_outbox_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS order_inventory_status;
CREATE TYPE order_inventory_status AS ENUM ('CHECKING', 'CHECKING_ROLLBACK','PROCESSING','PROCESSING_ROLLBACK');

DROP TABLE IF EXISTS "order".inventory_outbox CASCADE;
CREATE TABLE "order".inventory_outbox
(
    id bigint NOT NULL,
    saga_id uuid NOT NULL,
    payload jsonb NOT NULL,
    order_inventory_status order_inventory_status NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    order_status order_status NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT inventory_outbox_pkey PRIMARY KEY (id)
);

