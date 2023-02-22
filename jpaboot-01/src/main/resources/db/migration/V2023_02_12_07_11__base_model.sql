drop table person;

create table person (
    id serial primary key,
    name varchar(100) not null,
    address varchar(100) not null,
    created timestamp not null default Now()
);
insert into person (name, address) select 'name' || i, 'address' || i from generate_series(1, 100) i;

create table product_type (
    id serial primary key,
    name varchar(80) not null
);
insert into product_type (name) select 'type' || i from generate_series(1, 10) i;

create table product (
    id serial primary key,
    name varchar(80) not null,
    price numeric(10,2) not null,
    product_type int not null references product_type(id)
);
insert into product (name, price, product_type) select 'product' || i, i, 1 from generate_series(1, 100) i;

create table supplier (
    id serial primary key,
    name varchar(100) not null,
    address varchar(100) not null
);
insert into supplier (name, address) select 'supplier' || i, 'address' || i from generate_series(1, 100) i;

create table orders (
    id serial primary key,
    number int not null unique,
    person_id int not null references person(id),
    order_date date not null
);
insert into "orders" (number, person_id, order_date) select i, i, '2020-01-01' from generate_series(1, 100) i;

create table order_line (
    id serial primary key,
    order_id int not null references "orders"(id),
    product_id int not null references product(id),
    quantity numeric(10,2) not null,
    price numeric(10,2) not null
);
insert into order_line (order_id, product_id, quantity, price) select i, i, i, i from generate_series(1, 100) i;

create table supplier_product (
    id serial primary key,
    supplier_id int not null references supplier(id),
    product_id int not null references product(id)
);
insert into supplier_product (supplier_id, product_id) select i, i from generate_series(1, 100) i;


