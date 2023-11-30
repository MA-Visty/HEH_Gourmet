-- ---- Procedures ---- --

-- Procedure to add a user
create or replace procedure heh.add_user(
    in p_last_name varchar(255),
    in p_first_name varchar(255),
    in p_email varchar(255),
    in p_password varchar(255)
)
begin
    insert into heh.users (last_name, first_name, email, password, role)
    values (p_last_name, p_first_name, p_email, p_password, 0);
end;

-- Procedure to add a vendor
create or replace procedure heh.add_vendor(
    in p_last_name varchar(255),
    in p_first_name varchar(255),
    in p_email varchar(255),
    in p_password varchar(255)
)
begin
    insert into heh.users (last_name, first_name, email, password, role)
    values (p_last_name, p_first_name, p_email, p_password, 1);
end;

-- Procedure to add an admin
create or replace procedure heh.add_admin(
    in p_last_name varchar(255),
    in p_first_name varchar(255),
    in p_email varchar(255),
    in p_password varchar(255)
)
begin
    insert into heh.users (last_name, first_name, email, password, role)
    values (p_last_name, p_first_name, p_email, p_password, 100);
end;

-- Procedure to remove a user
create or replace procedure heh.remove_user(
    in p_user_id int
)
begin
    delete
    from heh.users
    where heh.users.user_id = p_user_id;
end;

-- Procedure to edit a user , values which are null are not updated
create or replace procedure heh.edit_user(
    in p_user_id int,
    in p_last_name varchar(255),
    in p_first_name varchar(255),
    in p_email varchar(255),
    in p_password varchar(255)
)
begin
    update heh.users
    set last_name  = ifnull(p_last_name, last_name),
        first_name = ifnull(p_first_name, first_name),
        email      = ifnull(p_email, email),
        password   = ifnull(p_password, password)
    where user_id = p_user_id;
end;


-- -- Category procedures -- --
-- Procedure to add a category
create or replace procedure heh.add_category(
    in p_name varchar(255),
    in p_description longtext
)
begin
    -- add category
    insert into heh.categories (name, description)
    values (p_name, p_description);
end;

-- Procedure to remove a category
create or replace procedure heh.remove_category(
    in p_id int
)
begin
    -- remove category
    delete from heh.categories where category_id = p_id;
end;

-- Procedure to edit a category , values which are null are not updated
create or replace procedure heh.edit_category(
    in p_id int,
    in p_name varchar(255),
    in p_description longtext
)
begin
    -- edit category
    update heh.categories
    set name        = ifnull(p_name, name),
        description = ifnull(p_description, description)
    where category_id = p_id;
end;

-- -- Product procedures -- --
-- Procedure to add a product with a new category
create or replace procedure heh.add_product_with_new_category(
    in p_name varchar(255),
    in p_description longtext,
    in p_price decimal,
    in p_stock int,
    in p_image varchar(255),
    in p_category_name varchar(255),
    in p_category_description longtext
)
begin
    -- add category
    call heh.add_category(p_category_name, p_category_description);
    -- get category id
    set @category_id = last_insert_id();
    -- add product
    call heh.add_product(p_name, p_description, p_price, ifnull(p_stock, 1), p_image,
                    @category_id);
end;

-- Procedure to add a product with an existing category
create or replace procedure heh.add_product(
    in p_name varchar(255),
    in p_description longtext,
    in p_price decimal,
    in p_stock int,
    in p_image varchar(255),
    in p_category_id int
)
begin
    -- add product
    insert into heh.products (name, description, price, stock, image, category_id)
    values (p_name, p_description, p_price, ifnull(p_stock, 1), p_image, p_category_id);
end;

-- Procedure to remove a product
create or replace procedure heh.remove_product(
    in p_id int
)
begin
    -- remove product
    delete from heh.products where product_id = p_id;
end;

-- Procedure to edit a product , values which are null are not updated
create or replace procedure heh.edit_product(
    in p_id int,
    in p_name varchar(255),
    in p_description longtext,
    in p_price decimal,
    in p_stock int,
    in p_image varchar(255),
    in p_category_id int
)
begin
    -- edit product
    update heh.products
    set name        = ifnull(p_name, name),
        description = ifnull(p_description, description),
        price       = ifnull(p_price, price),
        stock       = ifnull(p_stock, stock),
        image       = ifnull(p_image, image),
        category_id = ifnull(p_category_id, category_id)
    where product_id = p_id;
end;

-- -- Cart procedures -- --
-- Procedure to add a product to a cart
create or replace procedure heh.add_product_to_cart(
    in p_user_id int,
    in p_product_id int,
    in p_quantity int
)
begin
    -- add product to cart
    insert into heh.carts (user_id, product_id, quantity)
    values (p_user_id, p_product_id, ifnull(p_quantity, 1));
end;

-- Procedure to remove a product from a cart
create or replace procedure heh.remove_product_from_cart(
    in p_user_id int,
    in p_product_id int
)
begin
    -- remove product from cart
    delete
    from heh.carts
    where heh.carts.user_id = p_user_id
      and heh.carts.product_id = p_product_id;
end;

call heh.add_product_with_new_category('test', 'test', 1, 1, 'test', 'test', 'test');

/*
TODO : WIP
-- -- Order procedures -- --
-- Procedure to place an order from the user's cart
create procedure heh.place_order(
    in p_user_id int,
    in p_prepare_date date
)
begin
    -- check if prepare date is valid
    set @cancel_end = (select value
                       from heh.config
                       where heh.config.name = 'cancel_end');
    if p_prepare_date <= date_add(curdate(), interval @cancel_end HOUR_MINUTE) then
        signal sqlstate '45000' set message_text = 'Prepare date is invalid';
    end if;
    -- place order
    -- NOTE : on insert , the order is placed using the cart content then the cart is emptied
    insert into heh.orders (user_id, order_date, prepare_date)
    values (p_user_id, now(), p_prepare_date);
end;

-- Procedure to cancel an order if status is cancellable
create procedure heh.cancel_order(
    in p_order_id int
)
begin
    -- cancel order
    update heh.orders
    set status = 0
    where order_id = p_order_id
      and status = 1;
end;

-- Procedure to edit an order
-- NOTE : this can only be used to edit the prepare date of an order if the status is cancellable
create procedure heh.edit_order(
    in p_order_id int,
    in p_prepare_date date
)
begin
    -- check if prepare date is valid
    set @cancel_end = (select value
                       from heh.config
                       where heh.config.name = 'cancel_end');
    if p_prepare_date <= date_add(curdate(), interval @cancel_end HOUR_MINUTE) then
        signal sqlstate '45000' set message_text = 'Prepare date is invalid';
    end if;
    -- edit order
    update heh.orders
    set prepare_date = p_prepare_date
    where order_id = p_order_id
      and status = 1;
end;
 */