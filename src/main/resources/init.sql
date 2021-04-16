-- create database security;

use security;

CREATE TABLE users (
  username varchar(15),
  password varchar(100),
  enabled tinyint(1),
  PRIMARY KEY (username)
) ;

CREATE TABLE authorities (
  username varchar(15),
  authority varchar(25),
  FOREIGN KEY (username) references users(username)
) ;

INSERT INTO security.users (username, password, enabled)
VALUES
	('john', '{noop}q', 1),
	('mary', '{noop}q', 1),
	('susan', '{noop}q', 1);

INSERT INTO security.authorities (username, authority)
VALUES
	('john', 'EMPLOYEE'),
    ('mary', 'EMPLOYEE'),
	('mary', 'MANAGER'),
	('susan', 'EMPLOYEE'),
    ('susan', 'MANAGER'),
	('susan', 'ADMIN');

