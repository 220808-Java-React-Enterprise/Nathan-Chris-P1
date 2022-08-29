/*Data Definition Language*/

/*Drop*/
drop table "users" cascade;


/*Create*/
create table "users" (
	username varchar not null,
	password varchar not null,
	is_admin boolean not null,
	
	constraint "pk_users"
		primary key (username)
);

/*Alter*/

/*Truncate*/

/*Comment*/

/*Rename*/
