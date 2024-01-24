package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.OrderRowMapper;
import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;
import be.heh.gourmet.application.port.in.exception.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }


    public void placeOrder(int userID, Date date) {
        Date now = new Date(System.currentTimeMillis());
        try {
            jdbc.update("INSERT INTO orders (user_id,order_date, prepare_date) VALUES (?,?, ?)", userID, now, date);
        } catch (Exception e) {
            throw new OrderException("Error while placing order", OrderException.Type.ORDER_NOT_PLACED, e);
        }
    }

    public void editStatus(int orderID, OrderStatus status) {
        try {
            jdbc.update("UPDATE orders SET status = ? WHERE order_id = ?", status.value(), orderID);
        } catch (DataAccessException e) {
            throw new OrderException("Error while editing order status", OrderException.Type.ORDER_NOT_UPDATED, e);
        }
    }

    public void editPrepareDate(int orderID, Date date) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public List<OrderRow> list() {
        try {
            return jdbc.query("""
                    SELECT orders.order_id,
                           orders.user_id,
                           orders.order_date,
                           orders.prepare_date,
                           orders.status,
                           -- virtual column to store the total price of the order
                           (select sum(orders_products.price * orders_products.quantity)
                            from orders_products
                            where orders_products.order_id = orders.order_id) as total FROM orders""", new OrderRowMapper());
        } catch (Exception e) {
            throw new OrderException("Error while listing orders", OrderException.Type.ORDER_NOT_FOUND, e);
        }
    }

    public List<OrderRow> list(OrderStatus status) {
        try {
            Logger logger = LoggerFactory.getLogger(OrderRepository.class);
            logger.info("status: " + status.value());
            return jdbc.query("""
                    SELECT orders.order_id,
                           orders.user_id,
                           orders.order_date,
                           orders.prepare_date,
                           orders.status,
                           -- virtual column to store the total price of the order
                           (select sum(orders_products.price * orders_products.quantity)
                            from orders_products
                            where orders_products.order_id = orders.order_id) as total FROM orders WHERE status = ?""", new OrderRowMapper(), status.value());
        } catch (Exception e) {
            throw new OrderException("Error while listing orders", OrderException.Type.ORDER_NOT_FOUND, e);
        }
    }

    public List<OrderRow> list(int userID) {
        try {
            return jdbc.query("""
                    SELECT orders.order_id,
                           orders.user_id,
                           orders.order_date,
                           orders.prepare_date,
                           orders.status,
                           -- virtual column to store the total price of the order
                           (select sum(orders_products.price * orders_products.quantity)
                            from orders_products
                            where orders_products.order_id = orders.order_id) as total FROM orders WHERE user_id = ?""", new OrderRowMapper(), userID);
        } catch (Exception e) {
            throw new OrderException("Error while listing orders", OrderException.Type.ORDER_NOT_FOUND, e);
        }
    }

    public List<OrderRow> list(int userID, OrderStatus status) {
        try {
            return jdbc.query("""
                    SELECT orders.order_id,
                           orders.user_id,
                           orders.order_date,
                           orders.prepare_date,
                           orders.status,
                           -- virtual column to store the total price of the order
                           (select sum(orders_products.price * orders_products.quantity)
                            from orders_products
                            where orders_products.order_id = orders.order_id) as total FROM orders WHERE user_id = ? AND status = ?""", new OrderRowMapper(), userID, status.value());
        } catch (Exception e) {
            throw new OrderException("Error while listing orders", OrderException.Type.ORDER_NOT_FOUND, e);
        }
    }

    public Optional<OrderRow> get(int orderID) {
        try {
            List<OrderRow> order = jdbc.query("""
                    SELECT orders.order_id,
                           orders.user_id,
                           orders.order_date,
                           orders.prepare_date,
                           orders.status,
                           -- virtual column to store the total price of the order
                           (select sum(orders_products.price * orders_products.quantity)
                            from orders_products
                            where orders_products.order_id = orders.order_id) as total FROM orders WHERE order_id = ?""", new OrderRowMapper(), orderID);
            if (order.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(order.get(0));
        } catch (Exception e) {
            throw new OrderException("Error while getting order", OrderException.Type.ORDER_NOT_FOUND, e);
        }
    }

    public Optional<OrderRow> get(int orderID, int userID) {
        try {
            List<OrderRow> order = jdbc.query("""
                    SELECT orders.order_id,
                           orders.user_id,
                           orders.order_date,
                           orders.prepare_date,
                           orders.status,
                           -- virtual column to store the total price of the order
                           (select sum(orders_products.price * orders_products.quantity)
                            from orders_products
                            where orders_products.order_id = orders.order_id) as total FROM orders WHERE order_id = ? AND user_id = ?""", new OrderRowMapper(), orderID, userID);
            if (order.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(order.get(0));
        } catch (Exception e) {
            throw new OrderException("Error while getting order", OrderException.Type.ORDER_NOT_FOUND, e);
        }
    }
}
