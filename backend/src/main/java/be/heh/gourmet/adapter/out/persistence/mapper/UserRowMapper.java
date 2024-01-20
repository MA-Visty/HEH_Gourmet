package be.heh.gourmet.adapter.out.persistence.mapper;

import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException, RuntimeException {
        Optional<Role> role = Role.from(rs.getInt("role"));
        if (role.isEmpty()) {
            throw new RuntimeException("Unknown role");
        }
        return new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
                rs.getString("email"), role.get());
    }
}
