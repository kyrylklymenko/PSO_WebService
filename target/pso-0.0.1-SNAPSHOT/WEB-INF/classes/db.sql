create table user
(
	id int not null auto_increment
		primary key,
	first_name varchar(50) null,
	last_name varchar(50) null,
	activated tinyint(1) default '0' null,
	email varchar(50) null,
	password varchar(150) null,
	activation_key varchar(50) null,
	role varchar(50) null
)
;

