-- ---- Tables ---- --

-- Table to store the config of the app
create table if not exists config
(
    name  varchar(255) not null
        primary key,
    value varchar(255) not null
);

-- Insert the default config
-- not cancelable after 11:00:00 and
-- cannot order after 10:30:00
insert into config (name, value)
values ('prepare_time', '11:00:00'),
       ('order_time', '10:30:00');


-- Table to store the available categories
create table if not exists categories
(
    category_id serial       not null
        primary key,
    name        varchar(255) not null
        unique,
    description text         not null,
    constraint category_name
        unique (name)
);

-- Table to store available products
create table if not exists products
(
    product_id  serial       not null
        primary key,
    name        varchar(255) not null
        unique,
    description text         not null,
    price       decimal      not null,
    stock       int          not null,
    image       varchar(255) not null,
    category_id bigint       not null,
    constraint product_fk
        foreign key (category_id) references categories (category_id)
);

-- Table to store the users
create table if not exists users
(
    user_id    serial       not null
        primary key,
    last_name  varchar(255) not null,
    first_name varchar(255) not null,
    email      varchar(255) not null
        unique,
    password   varchar(255) not null,
    -- 0 = user, 1 = vendor, 100 = admin
    role       int          not null,
    constraint full_name
        unique (last_name, first_name)
);

-- Table to store the cart of each user
create table if not exists carts
(
    user_id    bigint not null,
    product_id bigint not null,
    quantity   int    not null default 1,
    constraint cart_pk
        primary key (user_id, product_id),
    constraint cart_product_fk
        foreign key (product_id) references products (product_id),
    constraint cart_user_fk
        foreign key (user_id) references users (user_id)
);

-- Table to store the favorites products of each user
create table if not exists favorites
(
    user_id    bigint not null,
    product_id bigint not null,
    constraint favorites_pk
        primary key (user_id, product_id),
    constraint favorites_product_fk
        foreign key (product_id) references products (product_id),
    constraint favorites_user_fk
        foreign key (user_id) references users (user_id)
);

-- Table to store the orders
create table if not exists orders
(
    order_id     serial not null
        primary key,
    user_id      bigint not null,
    order_date   date   not null,
    prepare_date date   not null,
    -- 0 = canceled, 1 = cancelable, 2 = pending, 3 = ready, 4 = delivered
    status       int    not null default 1,
    constraint order_user_fk
        foreign key (user_id) references users (user_id)
);

-- Table to store the products of each order
-- INFO : this doesn't depend on the product table because we want to keep the products even if they are deleted
create table if not exists orders_products
(
    order_id     bigint       not null,
    product_name varchar(255) not null
        unique,
    description  text         not null,
    quantity     int          not null default 1,
    price        decimal      not null,
    constraint order_products_pk
        primary key (order_id, product_name),
    constraint order_products_order_fk
        foreign key (order_id) references orders (order_id)
);

-- ---- Triggers ---- --

-- Trigger to remove products when category is deleted
-- WARN : this remove the category , the associated products and the associated cart
CREATE TRIGGER remove_category
    BEFORE DELETE
    ON categories
    FOR EACH ROW
EXECUTE PROCEDURE remove_product_trigger();

CREATE OR REPLACE FUNCTION remove_product_trigger() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM products WHERE products.category_id = OLD.category_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger to remove product when it's deleted
CREATE TRIGGER remove_product
    BEFORE DELETE
    ON products
    FOR EACH ROW
EXECUTE PROCEDURE remove_product_from_cart_and_favorites();

CREATE OR REPLACE FUNCTION remove_product_from_cart_and_favorites() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM carts WHERE carts.product_id = OLD.product_id;
    DELETE FROM favorites WHERE favorites.product_id = OLD.product_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger to remove cart when user is deleted it also remove the orders history
CREATE TRIGGER remove_user
    BEFORE DELETE
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE remove_user_from_carts_and_orders();

CREATE OR REPLACE FUNCTION remove_user_from_carts_and_orders() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM carts WHERE carts.user_id = OLD.user_id;
    DELETE FROM orders WHERE orders.user_id = OLD.user_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger to populate order_product when order is added and empty the cart
-- INFO : this is triggered when the order is passed
CREATE TRIGGER populate_orders_products
    AFTER INSERT
    ON orders
    FOR EACH ROW
EXECUTE PROCEDURE populate_orders_products_and_empty_cart();

CREATE OR REPLACE FUNCTION populate_orders_products_and_empty_cart() RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO orders_products (order_id, product_name, description, price)
    SELECT NEW.order_id, products.name, products.description, products.price
    FROM carts
             INNER JOIN products ON carts.product_id = products.product_id
    WHERE carts.user_id = NEW.user_id;

    DELETE FROM carts WHERE carts.user_id = NEW.user_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ---- Views ---- --

-- View to get the products of the cart of a user
create or replace view carts_products
as
select carts.user_id,
       carts.product_id,
       carts.quantity,
       products.name,
       products.price,
       products.price * carts.quantity as total_price
from carts
         inner join products on carts.product_id = products.product_id;


-- View to get the orders which are canceled using join
create or replace view orders_canceled
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from orders_products
        where orders_products.order_id = orders.order_id) as total
from orders
where orders.status = 0;


-- View to get the orders which are cancelable
create or replace view orders_cancelable
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from orders_products
        where orders_products.order_id = orders.order_id) as total
from orders
where orders.status = 1;

-- View to get the orders waiting to be prepared
create or replace view orders_pending
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from orders_products
        where orders_products.order_id = orders.order_id) as total
from orders
where orders.status = 2;

-- View to get the orders waiting to be delivered
create or replace view orders_ready
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from orders_products
        where orders_products.order_id = orders.order_id) as total
from orders
where orders.status = 3;

-- View to get the orders delivered
create or replace view orders_delivered
as
select orders.order_id,
       orders.user_id,
       orders.order_date,
       orders.prepare_date,
       -- virtual column to store the total price of the order
       (select sum(orders_products.price * orders_products.quantity)
        from orders_products
        where orders_products.order_id = orders.order_id) as total
from orders
where orders.status = 4;

-- ---- Procedure ---- --

-- Procedure to update the status of orders if needed
create or replace procedure update_orders_status()
as
$$
begin
    -- Update the status of orders if the prepare date is before the prepare date and before the time set in the config table
    update orders
    set status = 2
    where orders.status = 1
      AND orders.prepare_date::timestamp <
          current_date + (SELECT value::interval FROM config WHERE name = 'prepare_time');

    update orders
    set status = 0
    where orders.status = 2
      AND orders.prepare_date < current_date;
end;
$$
    LANGUAGE plpgsql;