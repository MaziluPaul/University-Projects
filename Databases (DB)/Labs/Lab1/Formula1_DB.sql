CREATE DATABASE Formula1_Code;
go

use Formula1_Code;

create table Car(
	CarId int primary key not null identity(1,1),
	EnginePower int,
	MaxSpeed int,
	FuelCapacity int,
	MaxRevs int,
);
go

create table Team(
	TeamId int primary key not null identity(1,1),
	TeamName text,
	Championships_Titles int,
	Total_Points int,
	id_car int foreign key references Car(CarId)
);
go


create table Driver(
	DriverId int primary key not null identity(1,1),
	DriverName text,
	Birthdate date,
	Country text,
	Points int,
	id_team int not null foreign key references Team(TeamId)
);
go



create table Track(
	TrackId int primary key not null identity(1,1),
	TrackName text,
	Country text,
	Turns int,
	Lenght float
);
go

create table Race(
	id_track int not null foreign key references Track(TrackId),
	id_driver int not null foreign key references Driver(DriverId),
	Date date,
	Laps int,
	Time time

	Constraint PK_Track_Driver Primary Key (id_track,id_driver)
);
go

insert into Car(EnginePower,MaxSpeed,FuelCapacity,MaxRevs) values
('1000','360','150','18000'),
('1090','370','150','19000')

insert into Team(TeamName,Championships_Titles,Total_Points,id_car) values
('Mercedes', '400', '4000', '1'),
('Ferrari', '700', '7000', '2')

insert into Driver(DriverName,Birthdate,Country,Points,id_team) values
('Hamilton', '1990-11-11', 'England', '1500','1'),
('Verstappen', '1997-09-30', 'Belgium', '2000','2'),
('Ricciardo', '1989-07-01', 'Australia', '1311','1')

insert into Track(TrackName,Country,Turns,Lenght) values
('Monza', 'Italy', '11', '5.793'),
('Lusail', 'Quatar', '16', '5.419'),
('Red Bull Ring', 'Australia', '10', '4.318')

insert into Race(id_track,id_driver,Date,Laps,Time) values
('2', '1', '2023-10-8', '57', '20:00:00'),
('1', '1','2023-8-10', '53', '10:00:00'),
('2', '2', '2023-10-8', '57', '20:00:00'),
('1', '3', '2023-8-10', '53', '10:00:00'),
('3', '2', '2023-6-20', '71', '13:00:00')
