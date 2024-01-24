package be.heh.gourmet.adapter.out.persistence.mapper;

import be.heh.gourmet.application.domain.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            return new Product(rs.getInt("product_id"), rs.getString("name"),
                    rs.getString("description"), rs.getFloat("price"),
                    // NB: rs.getUrl() is not implemented in the JDBC driver of PostgreSQL
                    rs.getInt("stock"), new URL(rs.getString("image")), rs.getInt("category_id"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
