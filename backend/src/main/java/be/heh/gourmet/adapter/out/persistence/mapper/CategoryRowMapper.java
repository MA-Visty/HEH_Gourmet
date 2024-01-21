package be.heh.gourmet.adapter.out.persistence.mapper;

import be.heh.gourmet.application.domain.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
