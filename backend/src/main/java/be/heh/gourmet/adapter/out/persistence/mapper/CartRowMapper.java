package be.heh.gourmet.adapter.out.persistence.mapper;

import be.heh.gourmet.application.domain.model.CartRow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<CartRow> {
    @Override
    public CartRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CartRow(
                rs.getInt("product_id"),
                rs.getString("name"),
                rs.getFloat("price"),
                rs.getInt("quantity"),
                rs.getFloat("total_price")
        );
    }
}
