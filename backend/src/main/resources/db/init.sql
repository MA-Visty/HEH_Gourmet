-- ---- Database ---- --

-- Main production database
create database heh
    default character set utf8;

-- ---- Tables and Triggers ---- --

-- Table to store the config of the app
-- INFO : this is used to store the config of the app like the hours at which the orders are not accepted anymore
-- or the hours at which the orders cannot be canceled anymore
create table heh.config
(
    config_id int auto_increment
        primary key,
    name      varchar(255) not null
        unique,
    value     varchar(255) not null
);

-- set the config of the app
insert into heh.config (name, value)
values ('cancel_end', '11:00:00');

-- Table to store the users
create table heh.users
(
    user_id    serial       not null
        primary key,
    -- virtual column to store the full name of the user
    full_name  varchar(511) generated always as (concat(last_name, ' ', first_name)) virtual invisible,
    last_name  varchar(255) not null,
    first_name varchar(255) not null,
    email      varchar(255) not null
        unique,
    password   varchar(255) not null,
    -- 0 = user, 1 = vendor, 100 = admin
    role       int          not null,
    constraint full_name
        unique (last_name, first_name)
) engine = InnoDB
  default charset = utf8;

-- Table to store the cart of each user
create table heh.carts
(
    user_id    int not null,
    product_id int not null,
    quantity   int not null default 1,
    constraint cart_pk
        primary key (user_id, product_id),
    constraint cart_product_fk
        foreign key (product_id) references heh.products (product_id),
    constraint cart_user_fk
        foreign key (user_id) references heh.users (user_id)
)
    engine = InnoDB
    default charset = utf8;

-- Trigger to remove cart when user is deleted it also remove the orders history
create trigger remove_user
    before delete
    on heh.users
    for each row
begin
    delete
    from heh.carts
    where heh.carts.user_id = old.user_id;
    delete
    from heh.orders
    where heh.orders.user_id = old.user_id;
end;

-- Table to store available products
create table heh.products
(
    product_id  serial       not null
        primary key,
    name        varchar(255) not null
        unique,
    description longtext     not null,
    price       decimal      not null,
    stock       int          not null,
    image       varchar(255) not null,
    category_id int          not null
        unique,
    constraint product_fk
        foreign key (category_id) references heh.categories (category_id)
)
    engine = InnoDB
    default charset = utf8;

-- Trigger to remove product when it's deleted
create trigger remove_product
    before delete
    on heh.products
    for each row
begin
    -- remove from cart
    delete
    from heh.carts
    where heh.carts.product_id = old.product_id;
    -- remove from favorites
    delete
    from heh.favorites
    where heh.favorites.product_id = old.product_id;
end;

-- Table to store the available categories
create table heh.categories
(
    category_id serial       not null
        primary key,
    name        varchar(255) not null
        unique,
    description longtext     not null,
    constraint category_name
        unique (name)
)
    engine = InnoDB
    default charset = utf8;

-- Trigger to remove products when category is deleted
-- WARN : this remove the category , the associated products and the associated cart
create trigger remove_category
    before delete
    on heh.categories
    for each row
begin
    delete
    from heh.products
    where heh.products.category_id = old.category_id;
end;

-- Table to store the favorites products of each user
create table heh.favorites
(
    user_id    int not null,
    product_id int not null,
    constraint favorites_pk
        primary key (user_id, product_id),
    constraint favorites_product_fk
        foreign key (product_id) references heh.products (product_id),
    constraint favorites_user_fk
        foreign key (user_id) references heh.users (user_id)
)
    engine = InnoDB
    default charset = utf8;

-- Table to store the orders
create table heh.orders
(
    order_id     serial   not null
        primary key,
    user_id      int      not null,
    order_date   datetime not null,
    prepare_date date     not null,
    -- 0 = canceled, 1 = cancelable, 2 = pending, 3 = ready, 4 = delivered
    status       int      not null default 1,
    constraint order_user_fk
        foreign key (user_id) references heh.users (user_id)
) engine = InnoDB
  default charset = utf8;

-- Trigger to set a event that set it as pending when it's cancel_end of the prepare_date and the status is 0
create trigger set_pending
    before insert
    on heh.orders
    for each row
begin
    -- set event to set status to pending
    set @event_name = concat('set_pending_', new.order_id);
    set @cancel_end = (select value
                       from heh.config
                       where heh.config.name = 'cancel_end');
    create event @event_name
        on schedule at date_add(new.prepare_date, interval @cancel_end HOUR_MINUTE) on completion not preserve
        do update heh.orders set status = 1 where order_id = new.order_id and status = 0;
end;

-- Trigger to remove the set_pending event when the order is cancelled
create trigger remove_pending
    before update
    on heh.orders
    for each row
begin
    if new.status = 0 then
        -- remove event
        set @event_name = concat('set_pending_', new.order_id);
        drop event @event_name;
    end if;
end;

-- Trigger to update the set_pending event when the prepare_date is updated
create trigger update_pending
    before update
    on heh.orders
    for each row
begin
    if old.prepare_date != new.prepare_date and old.status = 1 then
        set @event_name = concat('set_pending_', new.order_id);
        set @cancel_end = (select value
                           from heh.config
                           where heh.config.name = 'cancel_end');
        alter event @event_name
            on schedule at date_add(new.prepare_date, interval @cancel_end HOUR_MINUTE);
    end if;
end;

-- Table to store the products of each order
-- INFO : this doesn't depend on the product table because we want to keep the products even if they are deleted
create table heh.orders_products
(
    order_id     int          not null,
    product_name varchar(255) not null
        unique,
    description  longtext     not null,
    quantity     int          not null default 1,
    price        decimal      not null,
    constraint order_products_pk
        primary key (order_id, product_name),
    constraint order_products_order_fk
        foreign key (order_id) references heh.orders (order_id)
)
    engine = InnoDB
    default charset = utf8;

-- Trigger to populate order_product when order is added and empty the cart
-- INFO : this is triggered when the order is passed
create trigger populate_orders_products
    after insert
    on heh.orders
    for each row
begin
    -- populate order_products from cart
    insert into heh.orders_products (order_id, product_name, description, price)
    select new.order_id, heh.products.name, heh.products.description, heh.products.price
    from heh.carts
             inner join heh.products on heh.carts.product_id = heh.products.product_id
    where heh.carts.user_id = new.user_id;
    -- empty cart
    delete
    from heh.carts
    where heh.carts.user_id = new.user_id;
end;


-- ---- Views ---- --

-- View to get the products of the cart of a user
create view heh.carts_products
as
select heh.carts.user_id,
       heh.carts.product_id,
       heh.carts.quantity,
       heh.products.name,
       heh.products.price,
       heh.products.price * heh.carts.quantity as total_price
from heh.carts
         inner join heh.products on heh.carts.product_id = heh.products.product_id;


-- View to get the orders which are canceled using join
create view heh.orders_canceled
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from heh.orders_products
        where heh.orders_products.order_id = orders.order_id) as total
from heh.orders
where orders.status = 0;


-- View to get the orders which are cancelable
create view heh.orders_cancelable
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from heh.orders_products
        where heh.orders_products.order_id = orders.order_id) as total
from heh.orders
where orders.status = 1;

-- View to get the orders waiting to be prepared
create view heh.orders_pending
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from heh.orders_products
        where heh.orders_products.order_id = orders.order_id) as total
from heh.orders
where orders.status = 2;

-- View to get the orders waiting to be delivered
create view heh.orders_ready
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from heh.orders_products
        where heh.orders_products.order_id = orders.order_id) as total
from heh.orders
where orders.status = 3;

-- View to get the orders delivered
create view heh.orders_delivered
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from heh.orders_products
        where heh.orders_products.order_id = orders.order_id) as total
from heh.orders
where orders.status = 4;