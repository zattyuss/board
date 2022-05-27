drop table if exists board_tbl; 

create table board_tbl(
	bno int auto_increment primary key, 
    title varchar(300) not null, 
    content text not null, 
    writer varchar(50) not null, 
    regDate timestamp default current_timestamp, 
	updateDate timestamp default current_timestamp
);

insert into board_tbl(title,content, writer)
values ('게시물 제목입니다.','배가 고픕니다.','거시기');
insert into board_tbl(title,content, writer)
values ('게시물 제목입니다.2','배가 고픕니다.2','거시기2');
insert into board_tbl(title,content, writer)
values ('게시물 제목입니다.3','배가 고픕니다.3','거시기3');
insert into board_tbl(title,content, writer)
values ('게시물 제목입니다.4','배가 고픕니다.4','거시기4');