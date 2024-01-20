package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.CartRowMapper;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.port.in.exception.CartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void add(int userID, int productID, int quantity) throws CartException {
        try {
            jdbc.update("INSERT INTO carts (user_id,product_id, quantity) VALUES (?,?, ?)", userID, productID, quantity);
        } catch (DataAccessException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new CartException("Product already in cart", CartException.Type.PRODUCT_ALREADY_IN_CART);
            }
            if (e.getMessage().contains("violates foreign key constraint \"cart_user_fk\"")) {
                throw new CartException("User not found", CartException.Type.ASSOCIATED_USER_NOT_FOUND);
            }
            if (e.getMessage().contains("violates foreign key constraint \"cart_product_fk\"")) {
                throw new CartException("Product not found", CartException.Type.ASSOCIATED_PRODUCT_NOT_FOUND);
            }
            throw new CartException("Error while adding product to cart", CartException.Type.PRODUCT_NOT_ADDED);
        } catch (Exception e) {
            throw new CartException("Error while adding product to cart", CartException.Type.PRODUCT_NOT_ADDED);
        }
    }

    public void remove(int userID, int productID) {
        jdbc.update("DELETE FROM carts WHERE product_id = ? AND user_id = ?", productID, userID);
    }

    public void update(int userID, int productID, int quantity) {
        jdbc.update("UPDATE carts SET quantity = ? WHERE product_id = ? AND user_id = ?", quantity, productID, userID);
    }

    public void clear(int userID) {
        jdbc.update("DELETE FROM carts WHERE user_id = ?", userID);
    }

    public boolean exists(int userID, int productID) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM carts WHERE product_id = ? AND user_id = ?)", Boolean.class, productID, userID));
    }

    public List<CartRow> list(int userID) {
        return jdbc.query("select * from carts_products where user_id = ?; ", new CartRowMapper(), userID);
    }

    public Optional<CartRow> get(int userID, int productID) {
        List<CartRow> cart = jdbc.query("select * from carts_products where user_id = ? AND product_id = ?; ", new CartRowMapper(), userID, productID);
        if (cart.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cart.get(0));
    }
}
