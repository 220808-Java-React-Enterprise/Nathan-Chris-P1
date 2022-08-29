DROP TABLE ers_user_roles CASCADE;
DROP TABLE ers_reimbursement_statuses CASCADE;
DROP TABLE ers_reimbursement_types CASCADE;
DROP TABLE ers_users CASCADE;
DROP TABLE ers_reimbursements CASCADE;

CREATE TABLE ers_user_roles (
	role_id varchar,
	role varchar UNIQUE,

	CONSTRAINT pk_role_id
		PRIMARY KEY (role_id)
);

CREATE TABLE ers_reimbursement_statuses (
	status_id varchar,
	status varchar UNIQUE,

	CONSTRAINT pk_status_id
		PRIMARY KEY (status_id)
);

CREATE TABLE ers_reimbursement_types (
	type_id varchar,
	type varchar UNIQUE,

	CONSTRAINT pk_type_id
		PRIMARY KEY (type_id)
);

CREATE TABLE ers_users (
	user_id varchar,
	username varchar NOT NULL UNIQUE,
	email varchar NOT NULL UNIQUE,
	password varchar NOT NULL,
	given_name varchar NOT NULL,
	surname varchar NOT NULL,
	is_active boolean DEFAULT TRUE,
	role_id varchar,

	CONSTRAINT pk_user_id
		PRIMARY KEY (user_id),

	CONSTRAINT fk_u_role_id
		FOREIGN key (role_id) REFERENCES ers_user_roles (role_id)
);

CREATE TABLE ers_reimbursements (
	reimb_id varchar,
	amount numeric(6, 2) NOT NULL,
	submitted timestamp NOT NULL,
	resolved timestamp,
	description varchar NOT NULL,
	receipt bytea,
	payment_id varchar,
	author_id varchar NOT NULL,
	resolver_id varchar,
	status_id varchar NOT NULL,
	type_id varchar NOT NULL,
	
	CONSTRAINT pk_rreimb_id
		PRIMARY KEY (reimb_id),
		
	CONSTRAINT fk_r_author_id
		FOREIGN KEY (author_id) REFERENCES ers_users (user_id),

	CONSTRAINT fk_r_resolver_id
		FOREIGN KEY (resolver_id) REFERENCES ers_users (user_id),

	CONSTRAINT fk_r_status_id
		FOREIGN KEY (status_id) REFERENCES ers_reimbursement_statuses (status_id),

	CONSTRAINT fk_r_type_id
		FOREIGN KEY (type_id) REFERENCES ers_reimbursement_types (type_id)
);