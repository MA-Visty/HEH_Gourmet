package be.heh.gourmet.adapter.out.persistence.mapper;

import be.heh.gourmet.application.domain.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(rs.getInt("product_id"), rs.getString("name"),
                rs.getString("description"), rs.getFloat("price"),
                rs.getInt("stock"), rs.getURL("image"), rs.getInt("category_id"));
    }
}
