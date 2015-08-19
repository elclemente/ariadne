drop table if exists tag; 

create table tag (
        id integer auto_increment,
        album varchar(255),
        artist varchar(255),
        genre integer,
        title varchar(255),
        track varchar(255),
        year varchar(255),
        file_id varchar(255),
        scan_id varchar(255),
        primary key (id)
)