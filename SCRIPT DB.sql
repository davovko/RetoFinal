CREATE TABLE costumers(
costumer_id serial NOT NULL,
identification_type_Id int NOT NULL,
identification_number varchar(30) NULL,
first_name varchar(30) NOT NULL,
middle_name varchar(30) NULL,
last_name varchar(30) NOT NULL,
second_last_name varchar(30) NULL,
email varchar(30) NOT NULL,	
date_of_birth date NOT NULL,
creation_date timestamp NOT NULL,
creation_user_id int NOT NULL,
CONSTRAINT pk_costumers PRIMARY KEY (costumer_id));

CREATE TABLE products(
product_id serial NOT NULL,
product_type bool NOT NULL,
account_number varchar(30) NOT NULL,
state_account bool NULL,
balance int NOT NULL,
available_balance int NOT NULL,
gmf_exempt bool NOT NULL,
creation_date timestamp NOT NULL,
creation_user_id int NOT NULL,
CONSTRAINT pk_products PRIMARY KEY (product_id));




