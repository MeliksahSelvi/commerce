DROP SCHEMA IF EXISTS "shipping" CASCADE;
CREATE SCHEMA "shipping";

DROP TYPE IF EXISTS status_type;
CREATE TYPE status_type AS ENUM ('ACTIVE', 'PASSIVE');

DROP TYPE IF EXISTS delivery_status;
CREATE TYPE delivery_status AS ENUM ('APPROVED', 'SHIPPED','CANCELLED','DELIVERED');

DROP TABLE IF EXISTS "shipping".shipping CASCADE;
CREATE TABLE "shipping".shipping
(
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    customer_id bigint NOT NULL,
    delivery_status delivery_status NOT NULL,
    status_type status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT shipping_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "shipping".order_item CASCADE;
CREATE TABLE "shipping".order_item
(
    id bigint NOT NULL,
    shipping_id bigint NOT NULL,
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

ALTER TABLE "shipping".order_item
    ADD CONSTRAINT "FK_SHIPPING_ID" FOREIGN KEY (shipping_id)
        REFERENCES "shipping".shipping (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS "shipping".address CASCADE;
CREATE TABLE "shipping".address
(
    id bigint NOT NULL,
    shipping_id bigint NOT NULL,
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

ALTER TABLE "shipping".address
    ADD CONSTRAINT "FK_SHIPPING_ID" FOREIGN KEY (shipping_id)
        REFERENCES "shipping".shipping (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;