SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS first_category_tb;
DROP TABLE IF EXISTS order_products_tb;
DROP TABLE IF EXISTS order_tb;
DROP TABLE IF EXISTS payment_tb;
DROP TABLE IF EXISTS person_tb;
DROP TABLE IF EXISTS product_image_tb;
DROP TABLE IF EXISTS product_tb;
DROP TABLE IF EXISTS qna_tb;
DROP TABLE IF EXISTS second_category_tb;
DROP TABLE IF EXISTS user_tb;




/* Create Tables */

CREATE TABLE first_category_tb
(
	id int,
	first_category_name varchar(15),
	created_at timestamp
);


CREATE TABLE order_products_tb
(
	id int,
	quantity int,
	price int,
	product_id int,
	product_name varchar(50)
);


CREATE TABLE order_tb
(
	id int NOT NULL,
	user_id int,
	purchase_date timestamp,
	cancel_date timestamp,
	created_at timestamp,
	PRIMARY KEY (id)
);


CREATE TABLE payment_tb
(
	id int NOT NULL,
	-- 거래id
	tid int COMMENT '거래id',
	order_id int,
	created_at timestamp,
	PRIMARY KEY (id)
);


CREATE TABLE person_tb
(
	id int NOT NULL,
	name varchar(20),
	address varchar(60),
	email varchar(30),
	phone_number int,
	birthday datetime,
	user_id int,
	created_at timestamp,
	PRIMARY KEY (id)
);


CREATE TABLE product_image_tb
(
	id int NOT NULL,
	product_id int,
	image varchar(150),
	is_thumbnail boolean,
	created_at timestamp,
	PRIMARY KEY (id)
);


CREATE TABLE product_tb
(
	id int,
	category_id int,
	product_name varchar(50),
	price int,
	product_feature longtext,
	quantity int,
	created_at timestamp
);


CREATE TABLE qna_tb
(
	id int NOT NULL,
	title varchar(50),
	content longtext,
	user_id int,
	created_at timestamp,
	PRIMARY KEY (id)
);


CREATE TABLE second_category_tb
(
	id int,
	second_category_name varchar(15),
	category_id int,
	created_at timestamp
);


CREATE TABLE user_tb
(
	id int NOT NULL AUTO_INCREMENT,
	username varchar(10),
	password varchar(50),
	is_kakao boolean,
	level int,
	created_at timestamp,
	PRIMARY KEY (id),
	UNIQUE (username, is_kakao)
);



