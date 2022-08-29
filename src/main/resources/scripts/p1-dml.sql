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
