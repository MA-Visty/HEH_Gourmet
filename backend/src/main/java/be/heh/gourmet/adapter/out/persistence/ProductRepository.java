package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.ProductRowMapper;
import be.heh.gourmet.application.domain.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {
    private static final String addSql = "INSERT INTO products (product_id, name, description, price, stock, image, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String removeSql = "DELETE FROM products WHERE product_id = ?";
    private static final String updateSql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, image = ?, category_id = ? WHERE product_id = ?";

    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void add(Product product) {
        jdbc.update(addSql, product.ID(), product.name(), product.description(), product.price(), product.stock(), product.image(), product.categoryID());
    }

    public void batchAdd(List<Product> products) {
        jdbc.batchUpdate(addSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Product product = products.get(i);
                ps.setInt(1, product.ID());
                ps.setString(2, product.name());
                ps.setString(3, product.description());
                ps.setFloat(4, product.price());
                ps.setInt(5, product.stock());
                ps.setURL(6, product.image());
                ps.setInt(7, product.categoryID());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        });

    }

    public void remove(int ID) {
        jdbc.update(removeSql, ID);
    }

    public void batchRemove(List<Integer> IDs) {
        jdbc.batchUpdate(removeSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, IDs.get(i));
            }

            @Override
            public int getBatchSize() {
                return IDs.size();
            }
        });
    }

    public void update(Product product) {
        jdbc.update(updateSql, product.name(), product.description(), product.price(), product.stock(), product.image(), product.categoryID(), product.ID());
    }


    public void batchUpdate(List<Product> products) {
        jdbc.batchUpdate(updateSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Product product = products.get(i);
                ps.setString(1, product.name());
                ps.setString(2, product.description());
                ps.setFloat(3, product.price());
                ps.setInt(4, product.stock());
                ps.setURL(5, product.image());
                ps.setInt(6, product.categoryID());
                ps.setInt(7, product.ID());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        });
    }

    // TODO: 2021-05-04 use Optional instead of null
    public Product get(int ID) {
        return jdbc.query("SELECT * FROM products WHERE product_id = ?", new ProductRowMapper(), ID).get(0);
    }

    // TODO: 2021-05-04 use Optional instead of null
    // TODO: 2021-05-04 implement batchGet
    public List<Product> batchGet(List<Integer> ID) {
        throw new UnsupportedOperationException();
    }

    public List<Product> getAll() {
        return jdbc.query("SELECT * FROM products", new ProductRowMapper());
    }

    public int count() {
        return jdbc.query("SELECT COUNT(*) FROM products", (rs, rowNum) -> rs.getInt(1)).get(0);
    }
}
