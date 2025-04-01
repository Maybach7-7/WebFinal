\c postgres;
DROP DATABASE IF EXISTS web;
CREATE DATABASE web;
\connect web

CREATE SCHEMA applications;
SET search_path = applications, public;

CREATE TABLE users(
	id serial,
	fullname text not null,
	email text not null,
	phone text not null,
	birthday date not null,
	gender text not null,
	biography text not null,
	primary key(id)
);


CREATE TABLE languages(
	id integer,
	name text not null,
	primary key(id)
);

INSERT INTO languages (id, name)
VALUES (1, 'pascal'),
(2,'c'),
(3,'c++'),
(4,'javascript'),
(5,'php'),
(6,'python'),
(7,'java'),
(8,'haskel'),
(9,'clojure'),
(10,'prolog'),
(11,'scala');


CREATE TABLE users_languages(
	user_id integer,
	language_id integer,
	primary key(user_id, language_id),
	foreign key(user_id) references users(id),
	foreign key(language_id) references languages(id)
);

CREATE TABLE users_credentials(
	user_id integer not null references users,
	login text not null,
	password text not null,
	salt text not null
);

CREATE TABLE users_sessions(
	session_id text primary key,
	user_id integer not null references users,
	created_at timestamp not null default current_timestamp,
	expires_at timestamp not null
);

ALTER DATABASE web SET search_path = applications, public;
