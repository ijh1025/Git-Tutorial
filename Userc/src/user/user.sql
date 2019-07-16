create database USERCHAT

create table users(
	userID varchar2(20) PRIMARY KEY,
	userPassword varchar2(20),
	userName varchar2(20),
	userAge NUMBER,
	userGender varchar2(20),
	userEmail varchar2(50),
	userProfile	varchar2(50)

)




insert into users values('b','1234','박병관',1,'','',null)

SELECT * FROM users

SELECT * FROM chat

delete from user

CREATE SEQUENCE tmp_seq START WITH 1 INCREMENT BY 1

drop SEQUENCE tmp_seq

create table chat(
	chatID NUMBER,
	fromID varchar2(20),
	toID varchar2(20),
	chatContent varchar2(100),
	chatTime date,
	chatRead NUMBER
)

insert into chat values(tmp_seq.nextval,'재발','되라','부탁이다',sysdate,0)

drop table chat

select * from chat where chatID in
 (select max(chatID) from chat where toID='cc' or
  fromID='cc' group by fromID,toID)
  
create table board(
	userID varchar2(20),
	boardID NUMBER primary key,
	boardTitle varchar2(50),
	boardContent varchar2(2048),
	boardDate DATE,
	boardHit NUMBER,
	boardFile varchar2(100),
	boardRealFile varchar2(100),
	boardGroup NUMBER,
	boardSequence NUMBER,
	boardLevel NUMBER,
	boardAvailable NUMBER
)




delete from board
drop table board
select * from board