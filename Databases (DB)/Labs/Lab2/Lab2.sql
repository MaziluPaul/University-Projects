CREATE DATABASE lab2;
USE lab2;
/*
DROP DATABASE lab2;
*/
IF OBJECT_ID(N'Film', N'U') IS NULL
CREATE TABLE Film (
	id int,
	name text,
	release_date DATE,
	rating DECIMAL(10, 2)
);

ALTER TABLE Film
ALTER COLUMN id int NOT NULL;

ALTER TABLE Film
ADD CONSTRAINT PK_Film PRIMARY KEY (id);

ALTER TABLE dbo.Film
ADD country text;


IF OBJECT_ID(N'Actor', N'U') IS NULL
CREATE TABLE Actor (
	id int NOT NULL PRIMARY KEY,
	name text
);

IF OBJECT_ID(N'Distribution', N'U') IS NULL
CREATE TABLE Distribution (
	id_film int NOT NULL,
	id_actor int NOT NULL,
	role text,
	CONSTRAINT PK_Distribution PRIMARY KEY (id_film,id_actor),
	FOREIGN KEY (id_film) REFERENCES Film(id),
	FOREIGN KEY (id_actor) REFERENCES Actor(id)
);

INSERT INTO Film(id, name, release_date, rating, country) VALUES
('1', 'A', '2006-10-20', '3.1234', 'UK'),
('2', 'B', '2010-9-15', '5.3452', 'DE'),
('3', 'C', '2021-8-10', '6.7562', 'RO'),
('4', 'D', '2023-7-5', '7.4322', 'FR')

ALTER TABLE FILM
ALTER COLUMN country varchar(10);

DELETE FROM Film
WHERE release_date < '2012-1-1' AND country='UK'

DELETE FROM Film
WHERE release_date < '2012-1-1' OR country ='FR'

UPDATE Film
SET rating = '10.00'
WHERE rating IS NOT NULL

INSERT INTO Film(id, name , release_date, rating, country) values
('4','D','2019-12-12','7.321','IT')

UPDATE Film
SET name = 'Pasta'
WHERE NOT rating > '9.00'

SELECT * FROM Film;

INSERT INTO Actor(id, name) VALUES
('1', 'Ana'),
('2', 'Max'),
('3', 'Edy'),
('4', 'Ortis')

INSERT INTO Distribution(id_film, id_actor, role) VALUES
('1','1','secundar'),
('2','2','antagonist'),
('3','3','background'),
('4','4','protagonist')





SELECT * FROM Actor;
SELECT * FROM Distribution;
