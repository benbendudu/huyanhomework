drop table t_coffees if exists;
drop table t_orders if exists;
drop table t_order_coffees if exists;

create table t_coffees (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    name varchar(255),
    price DOUBLE,
    primary key (id)
);

create table t_orders (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    customer varchar(255),
    state integer not null,
    primary key (id)
);

create table t_order_coffees (
    coffee_order_id bigint not null,
    coffees_id bigint not null
);
