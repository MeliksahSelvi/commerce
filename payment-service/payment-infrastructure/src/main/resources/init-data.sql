INSERT INTO payment.account(id, customer_id, current_balance,currency_type,iban_no,cancel_date,status_type,created_at,updated_at)
VALUES (1,1,10000.00,'TL','TR260006278794193236123947',null,'ACTIVE',current_timestamp,null);

INSERT INTO payment.account_activity(id, account_id, cost,transaction_date,current_balance,activity_type,status_type,created_at,updated_at)
VALUES (1,1,10000.00,current_timestamp,10000.00,'DEPOSIT','ACTIVE',current_timestamp,null);




