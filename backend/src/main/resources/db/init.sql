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
-- schedule time is every 15 minutes
insert into config (name, value)
values ('prepare_time', '11:00:00'),
       ('order_time', '10:30:00'),
       ('schedule_time', (interval '15 minute')::text);

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
    user_id    serial       not null primary key,
    last_name  varchar(255) not null,
    first_name varchar(255) not null,
    email      varchar(255) not null
        unique,
    -- 0 = user, 1 = vendor, 100 = admin
    role       int          not null,
    constraint full_name
        unique (last_name, first_name)
);

-- Table to store the cart of each user
create table if not exists carts
(
    user_id    serial not null,
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
    user_id    serial not null,
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
    user_id      serial not null,
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
    product_name varchar(255) not null,
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
CREATE OR REPLACE FUNCTION remove_product_trigger() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM products WHERE products.category_id = OLD.category_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER remove_category
    BEFORE DELETE
    ON categories
    FOR EACH ROW
EXECUTE PROCEDURE remove_product_trigger();

-- Trigger to remove product when it's deleted
CREATE OR REPLACE FUNCTION remove_product_from_cart_and_favorites() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM carts WHERE carts.product_id = OLD.product_id;
    DELETE FROM favorites WHERE favorites.product_id = OLD.product_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER remove_product
    BEFORE DELETE
    ON products
    FOR EACH ROW
EXECUTE PROCEDURE remove_product_from_cart_and_favorites();

-- Trigger to remove cart when user is deleted it also remove the orders history
CREATE OR REPLACE FUNCTION remove_user_from_carts_favorites_and_orders() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM carts WHERE carts.user_id = OLD.user_id;
    DELETE FROM favorites WHERE favorites.user_id = OLD.user_id;
    DELETE FROM orders WHERE orders.user_id = OLD.user_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER remove_user
    BEFORE DELETE
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE remove_user_from_carts_favorites_and_orders();
-- Trigger to populate order_product when order is added and empty the cart
-- INFO : this is triggered when the order is passed
CREATE OR REPLACE FUNCTION populate_orders_products_and_empty_cart() RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO orders_products (order_id, product_name, description, price, quantity)
    SELECT NEW.order_id, products.name, products.description, products.price, carts.quantity
    FROM carts
             INNER JOIN products ON carts.product_id = products.product_id
    WHERE carts.user_id = NEW.user_id;

    DELETE FROM carts WHERE carts.user_id = NEW.user_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER populate_orders_products
    AFTER INSERT
    ON orders
    FOR EACH ROW
EXECUTE PROCEDURE populate_orders_products_and_empty_cart();

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

-- -- Chore procedures -- --

-- Procedure to update the status of orders if needed
-- this is intended to be called by a scheduled task every schedule_time ( see config table )
create or replace procedure update_orders_status()
as
$$
begin
    -- update orders if the current time is greater than the prepare date at the prepare time
    update orders
    set status = 2
    where orders.status = 1
      AND orders.prepare_date + (SELECT value::interval FROM config WHERE name = 'prepare_time') >
          now()::timestamp;

    -- autocancel orders if the current time is greater than the prepare date at the cancel time
    update orders
    set status = 0
    where orders.status = 2
      AND orders.prepare_date < current_date;
end;
$$ LANGUAGE plpgsql;

create or replace procedure place_order(
    in p_user_id varchar(255),
    in p_prepare_date date
) as
$$
begin
    -- if user has a cart and contains at least one product place order else throw an error
    if exists(select 1 from carts where carts.user_id = p_user_id) then
        if p_prepare_date is null then
            raise exception 'Prepare date cannot be null';
        end if;
    else
        raise exception 'Cart is empty';
    end if;

    -- add order
    insert into orders (user_id, order_date, prepare_date)
    values (p_user_id, current_date, p_prepare_date);
end;
$$ language plpgsql;


-- -- Users procedures -- --

-- Procedure to edit a user , values which are null are not updated
create or replace procedure edit_user(
    in p_user_id bigint,
    in p_last_name varchar(255),
    in p_first_name varchar(255),
    in p_email varchar(255)
) as
$$
begin
    -- edit user
    update users
    set last_name  = coalesce(p_last_name, last_name),
        first_name = coalesce(p_first_name, first_name),
        email      = coalesce(p_email, email)
    where users.user_id = p_user_id;
end;
$$ language plpgsql;

-- -- Category procedures -- --
-- Procedure to add a category
create or replace procedure add_category(
    in p_name varchar(255),
    in p_description text
) as
$$
begin
    insert into categories (name, description)
    values (p_name, p_description);
end;
$$ language plpgsql;

-- Procedure to remove a category
create or replace procedure remove_category(
    in p_category_id bigint
) as
$$
begin
    delete from categories where categories.category_id = p_category_id;
end;
$$ language plpgsql;

-- Procedure to edit a category , values which are null are not updated
create or replace procedure edit_category(
    in p_category_id bigint,
    in p_name varchar(255),
    in p_description text
) as
$$
begin
    update categories
    set name        = coalesce(p_name, name),
        description = coalesce(p_description, description)
    where categories.category_id = p_category_id;
end;
$$ language plpgsql;

-- -- Product procedures -- --
-- Procedure to add a product with a new category
create or replace procedure add_product_with_new_category(
    in p_name varchar(255),
    in p_description text,
    in p_price decimal,
    in p_stock int,
    in p_image varchar(255),
    in p_category_name varchar(255),
    in p_category_description text
) as
$$
declare
    category_id bigint;
begin
    -- add category
    insert into categories (name, description)
    values (p_category_name, p_category_description)
    RETURNING category_id into category_id;
    -- add product
    call add_product(p_name, p_description, p_price, coalesce(p_stock, 1), p_image,
                     category_id);
end;
$$ language plpgsql;

-- Procedure to add a product with an existing category
create or replace procedure add_product(
    in p_name varchar(255),
    in p_description text,
    in p_price decimal,
    in p_stock int,
    in p_image varchar(255),
    in p_category_id int
) as
$$
begin
    -- add product
    insert into products (name, description, price, stock, image, category_id)
    values (p_name, p_description, p_price, coalesce(p_stock, 1), p_image, p_category_id);
end;
$$ language plpgsql;

-- Procedure to remove a product
create or replace procedure remove_product(
    in p_id int
) as
$$
begin
    -- remove product
    delete from products where product_id = p_id;
end;
$$ language plpgsql;

-- Procedure to edit a product , values which are null are not updated
create or replace procedure edit_product(
    in p_id int,
    in p_name varchar(255),
    in p_description text,
    in p_price decimal,
    in p_stock int,
    in p_image varchar(255),
    in p_category_id int
) as
$$
begin
    -- edit product
    update products
    set name        = coalesce(p_name, name),
        description = coalesce(p_description, description),
        price       = coalesce(p_price, price),
        stock       = coalesce(p_stock, stock),
        image       = coalesce(p_image, image),
        category_id = coalesce(p_category_id, category_id)
    where product_id = p_id;
end;
$$ language plpgsql;


-- -- Cart procedures -- --
-- Procedure to add a product to a cart
create or replace procedure add_product_to_cart(
    in p_user_id int,
    in p_product_id int,
    in p_quantity int
) as
$$
begin
    -- add product to cart
    insert into carts (user_id, product_id, quantity)
    values (p_user_id, p_product_id, coalesce(p_quantity, 1));
end;
$$ language plpgsql;

-- Procedure to remove a product from a cart
create or replace procedure remove_product_from_cart(
    in p_user_id int,
    in p_product_id int
) as
$$
begin
    -- remove product from cart
    delete
    from carts
    where carts.user_id = p_user_id
      and carts.product_id = p_product_id;
end;
$$ language plpgsql;