insert into "order".orders(id, customer_id, cost, order_status, failure_messages, status_type, created_at, updated_at)
VALUES(1,1,10.0,'CHECKING','','ACTIVE',current_timestamp,null),
      (2,1,10.0,'APPROVED','','ACTIVE',current_timestamp,null);

insert into "order".order_item(id, order_id, product_id, price, quantity, total_price, status_type, created_at, updated_at)
values (1,1,1,10.0,1,10.0,'ACTIVE',current_timestamp,null),
       (2,2,1,10.0,1,10.0,'ACTIVE',current_timestamp,null);

insert into "order".address(id, order_id, city, county, neighborhood, street, postal_code, status_type, created_at, updated_at)
values (1,1,'denemecity','county','neigborhood','street','34001','ACTIVE',current_timestamp,null),
       (2,2,'denemecity2','county2','neigborhood2','street2','34002','ACTIVE',current_timestamp,null);