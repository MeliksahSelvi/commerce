insert into "shipping".shipping (id, order_id, customer_id, delivery_status, status_type, created_at, updated_at)
values (1,1,1,'APPROVED','ACTIVE',current_timestamp,null);

insert into "shipping".address(id, shipping_id, order_id, city, county, neighborhood, street, postal_code, status_type, created_at, updated_at)
values (1,1,1,'denemecity','county','neigborhood','street','34001','ACTIVE',current_timestamp,null);