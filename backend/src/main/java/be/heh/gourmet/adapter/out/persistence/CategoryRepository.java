package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.exception.CategoryException;
import be.heh.gourmet.adapter.out.persistence.mapper.CategoryRowMapper;
import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.port.in.InputCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class CategoryRepository {
    private static final String addSql = "INSERT INTO categories (name,description) VALUES (?, ?)";
    private static final String removeSql = "DELETE FROM categories WHERE category_id = ?";
    private static final String updateSql = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";

    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public int add(InputCategory category) throws CategoryException {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbc.update(
                    con -> {
                        PreparedStatement statement = con.prepareStatement(addSql, new String[]{"category_id"});
                        statement.setString(1, category.name());
                        statement.setString(2, category.description());
                        return statement;
                    }, holder);
            return Objects.requireNonNull(holder.getKey()).intValue();
        } catch (Exception e) {
            throw new CategoryException("Error while adding category", CategoryException.Type.CATEGORY_NOT_CREATED, e);
        }
    }

    public void batchAdd(List<InputCategory> categories) throws CategoryException {
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

    public void update(int ID, InputCategory category) throws CategoryException {
        try {
            int affectedRow = jdbc.update(updateSql, category.name(), category.description(), ID);
            if (affectedRow == 0) {
                throw new CategoryException("Category does not exist", CategoryException.Type.CATEGORY_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            if (e.getMessage() != null && e.getMessage().contains("No value present")) {
                throw new CategoryException("Category does not exist", CategoryException.Type.CATEGORY_NOT_FOUND, e);
            }
            throw new CategoryException("Error while updating category", CategoryException.Type.CATEGORY_NOT_UPDATED, e);
        }
    }

    public void remove(int ID) throws CategoryException {
        try {
            int affectedRow = jdbc.update(removeSql, ID);
            if (affectedRow == 0) {
                throw new CategoryException("Category does not exist", CategoryException.Type.CATEGORY_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            if (e.getMessage() != null && e.getMessage().contains("No value present")) {
                throw new CategoryException("Category does not exist", CategoryException.Type.CATEGORY_NOT_FOUND, e);
            }
            throw new CategoryException("Error while updating category", CategoryException.Type.CATEGORY_NOT_UPDATED, e);
        }
    }

    public void batchRemove(List<Integer> IDs) throws CategoryException {
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
            throw new CategoryException("Error while removing categories", CategoryException.Type.CATEGORY_NOT_DELETED, e);
        }
    }

    public Optional<Category> get(int ID) {
        List<Category> categories = jdbc.query("SELECT * FROM categories WHERE category_id = ? LIMIT 1", new CategoryRowMapper(), ID);
        if (categories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(categories.get(0));
    }

    public List<Category> batchGet(List<Integer> IDs) {
        return jdbc.query("SELECT * FROM categories WHERE category_id IN (?)", new CategoryRowMapper(), IDs);
    }

    public List<Category> getAll() {
        return jdbc.query("SELECT * FROM categories", new CategoryRowMapper());
    }

    public Category getByProduct(int ID) {
        List<Category> categories = jdbc.query("SELECT * FROM categories WHERE category_id = (SELECT category_id FROM products WHERE product_id = ?)", new CategoryRowMapper(), ID);
        if (categories.isEmpty()) {
            throw new CategoryException("Associated category not found", CategoryException.Type.ASSOCIATED_PRODUCT_NOT_FOUND);
        }
        return categories.get(0);
    }
}
