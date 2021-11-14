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

create table Booked(rID int, cID int, type text, checkIn date, checkOut date, PRIMARY KEY (rID), 
foreign key (rID, type) references Room (rID, type) on update cascade on delete cascade,
foreign key (cID) references Guest (cID) on update cascade on delete set null);
	/* to be updated */
    
create table Available(rID int, type text, serviceCode int, PRIMARY KEY (rID),
foreign key (rID, type) references Room (rID, type) on update cascade on delete cascade,
foreign key (serviceCode) references Service (serviceCode) on update cascade on delete set null);
	/* to be updated */
    
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
( 205, 'triple', 130 );

	/* 001: special meal, 002: minibar, 004: spa */
insert into Service values (101, 002, 'minibar'),
                    (102, 004, 'spa'),
                    (103, 001, 'special meal'),
                    (104, 004, 'spa'),
                    (105, 002, 'minibar'),
                    (201, 004, 'spa'),
                    (202, 004, 'spa'),
                    (203, 001, 'special meal'),
                    (204, 001, 'special meal'),
                    (205, 002, 'minibar');

/*
insert into Booked values
	/* to be updated */
/*
insert into Available values
	/* to be updated */

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
	where number = old.rID; 
end;
//

DROP TRIGGER IF EXISTS BookedTrigger;
delimiter //
CREATE TRIGGER BookedTrigger
AFTER INSERT ON Booked
FOR EACH ROW 
BEGIN
    DELETE FROM Available
    WHERE number = OLD.rID;
END;
//

DROP TRIGGER IF EXISTS AvailableTrigger;
delimiter //
CREATE TRIGGER AvailableTrigger
AFTER INSERT ON Available
FOR EACH ROW
BEGIN
    DELETE FROM Booked
    WHERE number = OLD.rID;
END;
//

drop trigger if exists InsertGuest;
delimiter //
create trigger InsertGuest
after insert on Guest
for each row
begin
	update Guest
    set updatedAt=current_date();
end;
//

drop trigger if exists UpdateGuest;
delimiter //
create trigger UpdateGuest
after update on Guest
for each row
begin
	update Guest
    set updatedAt=current_date();
end;
//

/* ARCHIVE FUNCTION */
drop procedure if exists Archive;
delimiter //
create procedure Archive(in cutoff date)
begin
	insert into ArchivedGuest (select * from Guest where updatedAt < cutoff);
    delete from Guest where updatedAt < cutoff;
end;
//
