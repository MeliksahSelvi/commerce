insert into "payment".account(id, customer_id, current_balance, currency_type, iban_no, cancel_date, status_type, created_at, updated_at)
values (1,1,10.0,'TL','123456789987654321',null,'ACTIVE',current_timestamp,null),
       (2,1,10.0,'TL','123456789987654322',null,'ACTIVE',current_timestamp,null);