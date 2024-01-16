package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.exception.ProductException;
import be.heh.gourmet.adapter.out.persistence.mapper.ProductRowMapper;
import be.heh.gourmet.application.port.in.InputProduct;
import be.heh.gourmet.application.domain.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductRepository {
    private static final String addSql = "INSERT INTO products (name, description, price, stock, image, category_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String removeSql = "DELETE FROM products WHERE product_id = ?";
    private static final String updateSql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, image = ?, category_id = ? WHERE product_id = ?";

    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public int add(InputProduct product) throws ProductException {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbc.update(
                    con -> {
                        PreparedStatement statement = con.prepareStatement(addSql, new String[]{"product_id"});
                        statement.setString(1, product.name());
                        statement.setString(2, product.description());
                        statement.setFloat(3, product.price());
                        statement.setInt(4, product.stock());
                        statement.setString(5, product.image().toString());
                        // statement.setURL(5, product.image());
                        statement.setInt(6, product.categoryID());
                        return statement;
                    }, holder);
            return Objects.requireNonNull(holder.getKey()).intValue();
        } catch (Exception e) {
            throw new ProductException("Error while adding product", ProductException.Type.PRODUCT_NOT_CREATED, e);
        }
    }

    public void batchAdd(List<InputProduct> products) throws ProductException {
        try {
            jdbc.batchUpdate(addSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    InputProduct product = products.get(i);
                    ps.setString(1, product.name());
                    ps.setString(2, product.description());
                    ps.setFloat(3, product.price());
                    ps.setInt(4, product.stock());
                    ps.setString(5, product.image().toString());
                    // ps.setURL(5, product.image());
                    ps.setInt(6, product.categoryID());
                }

                @Override
                public int getBatchSize() {
                    return products.size();
                }
            });
        } catch (Exception e) {
            throw new ProductException("Error while adding product", ProductException.Type.PRODUCT_NOT_CREATED, e);
        }
    }

    public void remove(int ID) throws ProductException {
        try {
            jdbc.update(removeSql, ID);
        } catch (Exception e) {
            throw new ProductException("Error while removing product", ProductException.Type.PRODUCT_NOT_DELETED, e);
        }
    }

    public void batchRemove(List<Integer> IDs) {
        try {
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
        } catch (Exception e) {
            throw new ProductException("Error while removing product", ProductException.Type.PRODUCT_NOT_DELETED, e);
        }
    }

    public void update(int ID, InputProduct product) {
        int affectedRows = jdbc.update(updateSql, product.name(), product.description(), product.price(), product.stock(), product.image(), product.categoryID(), ID);
        if (affectedRows == 0) {
            throw new ProductException("Error while updating product", ProductException.Type.PRODUCT_NOT_UPDATED);
        }
    }

    // TODO: 2021-05-04 use Optional instead of null
    public Optional<Product> get(int ID) {
        List<Product> products = jdbc.query("SELECT * FROM products WHERE product_id = ? LIMIT 1", new ProductRowMapper(), ID);
        if (products.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(products.get(0));
    }

    // TODO: 2021-05-04 use Optional instead of null
    public List<Product> batchGet(List<Integer> ID) {
        return jdbc.query("SELECT * FROM products WHERE product_id IN (?)", new ProductRowMapper(), ID);
    }

    public List<Product> getAll() {
        return jdbc.query("SELECT * FROM products", new ProductRowMapper());
    }

    public List<Product> getFromCategory(int categoryID) {
        return jdbc.query("SELECT * FROM products WHERE category_id = ?", new ProductRowMapper(), categoryID);
    }

    public int count() {
        return jdbc.query("SELECT COUNT(*) FROM products", (rs, rowNum) -> rs.getInt(1)).get(0);
    }
}
