/* DELETE THE DATABASE IF IT ALREADY EXISTS, THEN CREATE AND USE DATABASE */
drop database if exists hotel_reservation;
create database hotel_reservation;
use hotel_reservation;

/* DELETE THE TABLES IF THEY ALREADY EXIST */
drop table if exists Guest;
drop table if exists Room;
drop table if exists Service;
drop table if exists Booked;
drop table if exists Available;
drop table if exists Employee;
drop table if exists Transaction;
drop table if exists ArchivedGuest;

/* CREATE THE SCHEMA FOR OUR TABLES */
create table Guest(cID int primary key, name text, age int, reservation int, 
updatedAt date default '2020-01-01');

create table Room(rID int primary key, type text, price int);

create table Service(rID int, serviceCode int, service text, primary key(rID), 
foreign key(rID) references Room(rID) on update cascade on delete cascade);

create table Booked(rID int, cID int, PRIMARY KEY (rID), date_booked date,
foreign key (rID) references Room (rID) on update cascade on delete cascade,
foreign key (cID) references Guest (cID) on update cascade on delete set null);

create table Available(rID int, PRIMARY KEY (rID), date_available date,
foreign key (rID) references Room (rID) on update cascade on delete cascade);

create table Employee(employee_ID int primary key, employee_name text, position text, phone_number text);

create table Transaction(transaction_id int primary key, cID int, rID int, days int, amount_due int,
 time_of_purchase datetime, checkIn date, checkOut date,
 foreign key (cID) references Guest (cID) on update cascade on delete set null,
 foreign key (rID) references Room (rID) on update cascade on delete set null);
 
create table ArchivedGuest(cID int primary key, name text, age int, reservation int, updatedAt date);
    
/* POPULATE THE TABLES WITH OUR DATA */
insert into Guest values
( 1001, 'Austin Lee', 35, 0, '2021-10-01' ),
( 1002, 'Ryan Baker', 52, 1, '2021-05-01' ),
( 1003, 'Julia Flores', 18, 0, '2021-10-09' ),
( 1004, 'Katherine Adams', 42, 0, '2021-09-01' ),
( 1005, 'Patrick Mitchell', 35, 1, '2021-09-01' ),
( 1006, 'Susan Clark', 25, 1, '2021-10-01' ),
( 1007, 'Daniel Reed', 21, 1, '2021-05-01' ),
( 1008, 'Angela Garcia', 37, 1, '2021-10-11' ),
( 1009, 'Thomas Price', 74, 0, '2021-10-01' ),
( 1010, 'Lois Brown', 68, 1, '2021-10-11' ),
( 1011, 'Denise Smith', 35, 1, '2021-09-01' ),
( 1012, 'Jesse James', 42, 1, '2021-10-01' );

insert into Room values
( 101, 'single', 80 ),
( 102, 'single', 85 ),
( 103, 'double', 100 ),
( 104, 'double', 105 ),
( 105, 'triple', 120 ),
( 201, 'single', 85 ),
( 202, 'single', 85 ),
( 203, 'double', 95 ),
( 204, 'double', 115 ),
( 205, 'triple', 130 ),
( 301, 'single', 80 ),
( 302, 'single', 80 ),
( 303, 'double', 105 ),
( 304, 'double', 120 ),
( 305, 'triple', 125 );

	/* 001: special meal, 002: minibar, 004: spa */
insert into Service values 
(101, 002, 'minibar'),
(102, 004, 'spa'),
(103, 001, 'special meal'),
(104, 004, 'spa'),
(105, 002, 'minibar'),
(201, 004, 'spa'),
(202, 004, 'spa'),
(203, 001, 'special meal'),
(204, 001, 'special meal'),
(205, 002, 'minibar'),
(301, 001, 'special meal'),
(302, 001, 'special meal'),
(303, 002, 'minibar'),
(304, 002, 'minibar'),
(305, 001, 'special meal');

insert into Booked values
(101, 1001, '2021-10-01'),
(102, 1002, '2021-10-01'),
(103, 1003, '2021-10-01'),
(201, 1004, '2021-10-01'),
(203, 1005, '2021-10-01'),
(301, 1006, '2021-10-01'),
(305, 1010, '2021-10-01');

insert into Available values
(104, '2021-10-01'),
(105, '2021-10-01'),
(202, '2021-10-01'),
(204, '2021-10-01'),
(205, '2021-10-01'),
(302, '2021-10-01'),
(303, '2021-10-01'),
(304, '2021-10-01');

insert into Employee values
( 0, 'Arnoldo Demetrio', 'Concierge', '555-1234' ),
( 1, 'Lila Prudenzio', 'Manager', '555-1111' ),
( 2, 'Stig Tahmina', 'Front Desk', '555-1112' ),
( 3, 'Erhan Jozafat', 'Valet', '555-1121' ),
( 4, 'Eini Finn', 'Janitor', '555-1211' ),
( 5, 'Melanija Javiera', 'Sales', '555-1311' ),
( 6, 'Suman Gundisalvus', 'Housekeeper', '555-1131' ),
( 7, 'Solly Ivanka', 'Security', '555-1114' );
                    
/* Making irrelevant information since this will be active data, has no bearance on the current available or booked entries */
insert into Transaction values
( 0, 1001, 101, 3, 235, '2021-10-11 10:34:09', '2021-10-11', '2021-10-14' ),
( 1, 1002, 102, 1, 75, '2021-11-11 22:34:09', '2021-11-11', '2021-11-12' ),
( 2, 1003, 103, 2, 155, '2021-10-11 5:22:09', '2021-10-12', '2021-10-14' ),
( 3, 1004, 104, 5, 455, '2021-05-11 10:34:09', '2021-05-11', '2021-05-16' ),
( 4, 1005, 105, 2, 125, '2021-10-11 10:34:09', '2021-10-11', '2021-10-13' ),
( 5, 1006, 201, 3, 345, '2021-10-11 10:34:09', '2021-10-11', '2021-10-14' ),
( 6, 1007, 202, 4, 535, '2021-10-11 10:34:09', '2021-10-11', '2021-10-15' ), 
( 7, 1008, 203, 5, 735, '2021-10-11 10:34:09', '2021-10-11', '2021-10-16' );

/* TRIGGERS */
drop trigger if exists ServiceD;
delimiter //
create trigger ServiceD 
after delete on Room 
for each row 
begin 
	delete from Service 
	where rID = old.rID; 
end;
//


DROP TRIGGER IF EXISTS BookedTrigger;
delimiter //
CREATE TRIGGER BookedTrigger
AFTER INSERT ON Booked
FOR EACH ROW 
BEGIN
    DELETE FROM Available
    WHERE rID = new.rID;
END;
//

DROP TRIGGER IF EXISTS AvailableTrigger;
delimiter //
CREATE TRIGGER AvailableTrigger
AFTER INSERT ON Available
FOR EACH ROW
BEGIN
    DELETE FROM Booked
    WHERE rID = new.rID;
END;
//

DROP TRIGGER IF EXISTS DelFromBooked//
delimiter //
CREATE TRIGGER DelFromBooked
AFTER DELETE ON Booked
FOR EACH ROW
BEGIN
    INSERT INTO Available values (OLD.rID, CURRENT_DATE());
END;
//

DROP TRIGGER IF EXISTS DelFromAvail//
delimiter //
CREATE TRIGGER DelFromAvail
AFTER DELETE ON Available
FOR EACH ROW
BEGIN
	INSERT INTO Booked values (OLD.rID, CURRENT_DATE());
END;
//

/* ARCHIVE PROCEDURE */
DROP PROCEDURE IF EXISTS Archive;
delimiter //
CREATE PROCEDURE Archive(IN cutoff date)
BEGIN
	insert into ArchivedGuest (select * from Guest where updatedAt < cutoff);
    delete from Guest where updatedAt < cutoff;
END;
//


/* FUNCTIONAL REQUIREMENTS */

/* Get list of guests in alphabetical order */
select cID, name, age from Guest order by name;

/* Find the cheapest double room */
select * from Room where type='double' and price <= all (select price from Room R2 where type='double');

/* Sort the rooms under $100 from cheapest to most expensive */
select * from Room where price < 100 order by price, rID;

/* Find types of available rooms where the maximum price is 100 */
select type, max(price) as max_price from Available join Room where Available.rID = Room.rID group by type having max(price) <= 100;

/* Need to display all employees for managers or HR, etc */
select * from Employee;

/* Select transactions where the check in date is in the future */
select * from Transaction where checkIn > curdate();

/* Someone needs to request room cleaning */
select * from Employee where position like 'housekeeper'; 

/* Check customer transaction history */
select * from Transaction where cID in ( select cID from Guest where name like 'customer name here');

/* Find all available single rooms: */
select * from Available where rID in (select rID from Room where type="single");

/* Find all available rooms with more than one bed: */
select * from Available where rID in (select rID from Room where type <> "single");

/* Find all available rooms that come with a spa: */
select * from Available where rID in (select Room.rID from Room, Service where Room.rID=Service.rID and service="spa");

/* Find all booked rooms with guests over 50: */
select * from Booked where cID in (select cID from Guest where age > 50);

/* Find all available rooms that request minibar for an extra service: */
select * from Room where rID in ( select Service.rID from Service, Available where Service.rID=Available.rID and service = 'minibar');

/* Find all available rooms that request spa or special meal for an extra service: */
select * from Room where rID in ( select Service.rID from Service, Available where Service.rID=Available.rID and (service = 'spa' or service = 'special meal'));

/* Find all single rooms that request spa for an extra service: */
select * from Room where rID in (select rID from Service where Room.rID = Service.rID and Room.type = 'single' and Service.service = 'spa');

/* Find all available service with triple room : */
select distinct service from Service where rID in (select rID from Room where Room.rID = Service.rID and Room.type = 'triple');
