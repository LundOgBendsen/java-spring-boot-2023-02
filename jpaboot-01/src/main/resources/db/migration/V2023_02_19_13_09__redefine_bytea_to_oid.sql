
--alter document.content to oid
alter table document drop column content;

--add new column content of type oid
alter table document add column content oid;
