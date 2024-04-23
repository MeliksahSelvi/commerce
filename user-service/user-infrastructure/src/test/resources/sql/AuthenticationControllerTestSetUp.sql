insert into "user".role(id, role_type, permissions, status_type, created_at, updated_at)
values (1,'ADMIN','SAVE','ACTIVE',current_timestamp,null),
       (2,'CUSTOMER','SAVE','ACTIVE',current_timestamp,null);

insert into "user"."user" (id, email, password, customer_id, role_id, status_type, created_at, updated_at)
values (100,'login.user@gmail.com','$2a$10$VhHLhVweA2NFyMXK.k8ZaeNPslMKZISckaSRpg7j9.I85kR101F1i',null,1,'ACTIVE',current_timestamp,null);