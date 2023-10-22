CREATE DATABASE Formula1_Code;
GO

USE Formula1_Code;

IF OBJECT_ID(N'Car', N'U') IS NULL
create table Car(
	CarId int primary key not null identity(1,1),
	EnginePower int,
	MaxSpeed int,
	FuelCapacity int,
	MaxRevs int,
);
go

IF OBJECT_ID(N'Team', N'U') IS NULL
create table Team(
	TeamId int primary key not null identity(1,1),
	TeamName text,
	Championships_Titles int,
	Total_Points int,
	id_car int foreign key references Car(CarId)
);
go

IF OBJECT_ID(N'Driver', N'U') IS NULL
create table Driver(
	DriverId int primary key not null identity(1,1),
	DriverName text,
	Birthdate date,
	Country text,
	Points int,
	id_team int not null foreign key references Team(TeamId)
);
go


IF OBJECT_ID(N'Track', N'U') IS NULL
create table Track(
	TrackId int primary key not null identity(1,1),
	TrackName text,
	Country text,
	Turns int,
	Lenght float
);
go

IF OBJECT_ID(N'Race', N'U') IS NULL
create table Race(
	id_track int not null foreign key references Track(TrackId),
	id_driver int not null foreign key references Driver(DriverId),
	RDate date,
	Laps int,
	Time time

	Constraint PK_Track_Driver Primary Key (id_track,id_driver)
);
go

insert into Car(EnginePower,MaxSpeed,FuelCapacity,MaxRevs) values
('1000','360','150','18000'),
('1090','370','150','19000'),
('900','270','140','17000')

insert into Team(TeamName,Championships_Titles,Total_Points,id_car) values
('Mercedes', '400', '4000', '1'),
('Ferrari', '700', '7000', '2'),
('Redbull', '300', '6000', '3')

insert into Driver(DriverName,Birthdate,Country,Points,id_team) values
('Hamilton', '1990-11-11', 'England', '1500','1'),
('Verstappen', '1997-09-30', 'Belgium', '2000','2'),
('Ricciardo', '1989-07-01', 'Australia', '1311','1')

ALTER TABLE Driver 
ALTER COLUMN Country varchar(10)

UPDATE Driver 
SET Points = 3000 
WHERE Country = 'England' OR id_team = '2'


insert into Track(TrackName,Country,Turns,Lenght) values
('Monza', 'Italy', '11', '5.793'),
('Lusail', 'Quatar', '16', '5.419'),
('Red Bull Ring', 'Australia', '10', '4.318'),
('Imola', 'Italy', '13', NULL)

ALTER TABLE Track 
ALTER COLUMN Country varchar(10)

UPDATE Track
SET Lenght = '-1'
WHERE Lenght IS NOT NULL

insert into Race(id_track,id_driver,RDate,Laps,Time) values
('2', '1', '2023-8-10', '57', '20:00:00'),
('1', '1', '2023-8-10', '56', '10:00:00'),
('2', '2', '2023-10-8', '53', '20:00:00'),
('1', '3', '2023-8-10', '53', '10:00:00'),
('3', '2', '2023-6-20', '71', '13:00:00')

UPDATE Race 
SET RDate = '2024-1-22' 
WHERE RDate = '2023-8-10'

DELETE Race 
WHERE NOT Laps > 55

DELETE Race
WHERE Time = '10:00:00' AND Laps = '56'

SELECT * FROM Race
SELECT * FROM Track
SELECT * FROM Driver

