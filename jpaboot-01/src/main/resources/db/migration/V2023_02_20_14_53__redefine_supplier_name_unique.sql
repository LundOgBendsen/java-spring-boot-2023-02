
--alter supplier.name to unique
alter table supplier add constraint supplier_name_key unique (name);
