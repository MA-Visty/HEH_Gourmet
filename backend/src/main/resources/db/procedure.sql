-- ---- Procedures ---- --
/*
TODO : LEGACY CODE
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