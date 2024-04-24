INSERT INTO "payment"."customer"(id, first_name, last_name, identity_no, email, password, status_type, created_at, updated_at)
VALUES (1,'Melikşah','Selvi','123456789','meliksah.selvi2834@gmail.com','$2a$10$VhHLhVweA2NFyMXK.k8ZaeNPslMKZISckaSRpg7j9.I85kR101F1i','ACTIVE',current_timestamp,null);

insert into "payment".account(id, customer_id, current_balance, currency_type, iban_no, cancel_date, status_type, created_at, updated_at)
values (100,1,10.0,'TL','123456789987654321',null,'ACTIVE',current_timestamp,null),
       (101,1,10.0,'TL','123456789987654322',null,'ACTIVE',current_timestamp,null);



