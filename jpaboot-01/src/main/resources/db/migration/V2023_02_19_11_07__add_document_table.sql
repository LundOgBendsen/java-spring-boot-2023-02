
--create a new table for binary files
create table document (
    id serial primary key,
    name varchar(100) not null,
    content bytea not null
);

--create an a table for associated keywords
create table document_keyword (
    id serial primary key,
    document_id int not null references document(id),
    keyword varchar(100) not null
);
