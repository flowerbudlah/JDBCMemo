-- 테이블 삭제
drop table useraccount;
drop table usermemo;
drop sequence memoseq;

--유저정보
create table useraccount (
	name varchar2(20) not null,			-- 회원이름 문자열 null존재불가
	id varchar2(20) primary key,		-- 아이디 null존재불가 
	password varchar2(20) not null		-- 비밀번호  null존재불가 
);

--메모일련번호
create sequence memoseq;

--메모정보
create table usermemo (
	memoseq int primary key,
    title varchar2(60) not null, 	--메모 제목
    content varchar2(2000) not null,--메모내용
    indate date default sysdate,    --메모작성일
    id varchar2(20),				--작성자
	constraint id_fk 
	foreign key (id) 
	references useraccount(id)	
);


-- 커밋
commit;


delete useraccount;
delete usermemo;

