/*Enum Definitions*/
INSERT INTO ers_reimbursement_statuses (status_id, status) VALUES ('PENDING', 'PENDING');
INSERT INTO ers_reimbursement_statuses (status_id, status) VALUES ('DENIED',' DENIED');
INSERT INTO ers_reimbursement_statuses (status_id, status) VALUES ('APPROVED', 'APPROVED');

INSERT INTO ers_reimbursement_types (type_id, type) VALUES ('LODGING', 'LODGING');
INSERT INTO ers_reimbursement_types (type_id, type) VALUES ('TRAVEL', 'TRAVEL');
INSERT INTO ers_reimbursement_types (type_id, type) VALUES ('FOOD', 'FOOD');
INSERT INTO ers_reimbursement_types (type_id, type) VALUES ('OTHER', 'OTHER');

INSERT INTO ers_user_roles  (role_id, role) VALUES ('ADMIN', 'ADMIN');
INSERT INTO ers_user_roles  (role_id, role) VALUES ('FINANCE_MANAGER', 'FINANCE_MANAGER');
INSERT INTO ers_user_roles  (role_id, role) VALUES ('EMPLOYEE', 'EMPLOYEE');

INSERT INTO ers_users (user_id,username,email,"password",given_name,surname,is_active,role_id) values
	 ('62546653-a281-4341-b37c-c33824085365','koukaakiva','koukaakiva@gmail.com','f1bd529511da428ea53af8a94e46bf33:DFDE9CF44F8246D3B715C945D4C9F2E8','Chris','Waters',true,'ADMIN'),
	 ('77ebf82a-02f6-49af-a284-fd1fa7f12739','tahg','nathan@gmail.com','0951e63a2c74475eb2d5c62295a0e5c9:A29FC630FC178C0186D6094FF2203550','Nathan','Gilbert',true,'FINANCE_MANAGER'),
	 ('0a892bb9-9d82-4750-b80e-b32d3da76429','dummyuser1','nathanthegreat@gmail.com','6f48a6af56a748e2ad46243fade6d03b:808A96BFB8A485C65407C36510B7F688','Nathan','The Great',true,'EMPLOYEE'),
	 ('22442037-fc88-4429-8811-0abb4589cd35','dummyuser2','christhewizard@gmail.com','938be9a2e9c341f6ace46c01ed4e8658:FD231DD6BA99A215AA61FB0B78E2C73D','Chris','The Wizard',false,'EMPLOYEE');
	
INSERT INTO ers_reimbursements (reimb_id,amount,submitted,resolved,description,receipt,payment_id,author_id,resolver_id,status_id,type_id) VALUES
	 ('c08acb95-916a-4b79-872d-f2f889a7f3a6',500.00,'2022-09-07 12:32:05.056',NULL,'Super Hilton',NULL,'3cb74ef5-1746-4921-8f82-ecb8d9858311','22442037-fc88-4429-8811-0abb4589cd35',NULL,'PENDING','LODGING'),
	 ('440b6f49-bc25-4a13-9899-fd4cde18d537',999.99,'2022-09-07 12:32:05.056',NULL,'Space, I want to go to Space!',NULL,'3cb74ef5-1746-4921-8f82-ecb8d9858311','22442037-fc88-4429-8811-0abb4589cd35',NULL,'PENDING','TRAVEL'),
	 ('fba9a68f-07d9-44bb-99c6-21a90e89dc6a',50.00,'2022-09-07 12:54:46.302','2022-09-07 16:00:19.934','I can has cheezburger?',NULL,'3cb74ef5-1746-4921-8f82-ecb8d9858311','0a892bb9-9d82-4750-b80e-b32d3da76429','77ebf82a-02f6-49af-a284-fd1fa7f12739','APPROVED','FOOD'),
	 ('4d46f303-f381-41c1-8258-cfe547325112',60.00,'2022-09-07 18:55:07.072','2022-09-07 19:52:37.472','I can has mellon?',NULL,'3cb74ef5-1746-4921-8f82-ecb8d9858311','22442037-fc88-4429-8811-0abb4589cd35','77ebf82a-02f6-49af-a284-fd1fa7f12739','APPROVED','FOOD'),
	 ('6580a33e-e352-4fdd-8963-8f3080df4ab0',600.00,'2022-09-07 12:54:46.302','2022-09-07 16:00:19.934','I can has mellon?',NULL,'3cb74ef5-1746-4921-8f82-ecb8d9858311','0a892bb9-9d82-4750-b80e-b32d3da76429','77ebf82a-02f6-49af-a284-fd1fa7f12739','DENIED','FOOD');

