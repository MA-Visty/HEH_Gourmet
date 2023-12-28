package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.CategoryRowMapper;
import be.heh.gourmet.adapter.out.persistence.mapper.ProductRowMapper;
import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.port.in.InputCategory;
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

@Repository
public class CategoryRepository {
    private static final String addSql = "INSERT INTO categories (name,description) VALUES (?, ?)";
    private static final String removeSql = "DELETE FROM categories WHERE category_id = ?";
    private static final String updateSql = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";

    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public int add(InputCategory category) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(
                con -> {
                    PreparedStatement statement = con.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, category.name());
                    statement.setString(2, category.description());
                    return statement;
                }, holder);
        return Objects.requireNonNull(holder.getKey()).intValue();
    }

    public void batchAdd(List<InputCategory> categories) {
        jdbc.batchUpdate(addSql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                InputCategory category = categories.get(i);
                ps.setString(1, category.name());
                ps.setString(2, category.description());
            }

            @Override
            public int getBatchSize() {
                return categories.size();
            }
        });
    }

    public void update(int ID, InputCategory category) {
        jdbc.update(updateSql, category.name(), category.description(), ID);
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

    public Category get(int ID) {
        return jdbc.query("SELECT * FROM categories WHERE category_id = ?", new CategoryRowMapper(), ID).get(0);
    }

    public List<Category> batchGet(List<Integer> IDs) {
        return jdbc.query("SELECT * FROM categories WHERE category_id IN (?)", new CategoryRowMapper(), IDs);
    }

    public List<Category> getAll() {
        return jdbc.query("SELECT * FROM categories", new CategoryRowMapper());
    }

    public Category getByProduct(int ID) {
        return jdbc.query("SELECT * FROM categories WHERE category_id = (SELECT category_id FROM products WHERE product_id = ?)", new CategoryRowMapper(), ID).get(0);
    }
}
