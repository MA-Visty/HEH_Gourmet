package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.CartRowMapper;
import be.heh.gourmet.application.domain.model.CartRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void add(String userID, int productID, int quantity) {
        jdbc.update("INSERT INTO carts (user_id,product_id, quantity) VALUES (?,?, ?)", userID, productID, quantity);
    }

    public void remove(String userID, int productID) {
        jdbc.update("DELETE FROM carts WHERE product_id = ? AND user_id = ?", productID, userID);
    }

    public void update(String userID, int productID, int quantity) {
        jdbc.update("UPDATE carts SET quantity = ? WHERE product_id = ? AND user_id = ?", quantity, productID, userID);
    }

    public void clear(String userID) {
        jdbc.update("DELETE FROM carts WHERE user_id = ?", userID);
    }

    public boolean exists(String userID, int productID) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM carts WHERE product_id = ? AND user_id = ?)", Boolean.class, productID, userID));
    }

    public List<CartRow> list(String userID) {
        return jdbc.query("select * from carts_products where user_id = ?; ", new CartRowMapper(), userID);
    }
}
