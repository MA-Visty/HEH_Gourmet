package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.UserRowMapper;
import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.in.InputUser;
import be.heh.gourmet.application.port.in.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public int add(InputUser user, Role role) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbc.update(
                    con -> {
                        PreparedStatement statement = con.prepareStatement("INSERT INTO users (first_name,last_name, email, role) VALUES (?, ?, ?, ?)", new String[]{"user_id"});
                        statement.setString(1, user.firstname());
                        statement.setString(2, user.lastname());
                        statement.setString(3, user.email());
                        statement.setInt(4, role.getValue());
                        return statement;
                    }, holder);
            return Objects.requireNonNull(holder.getKey()).intValue();
        } catch (Exception e) {
            throw new UserException("Error while adding user", UserException.Type.USER_NOT_CREATED, e);
        }
    }

    public Optional<User> getByEmail(String email) {
        try {

            List<User> res = jdbc.query("SELECT * FROM users WHERE email = ? LIMIT 1", new UserRowMapper(), email);
            if (res.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(res.get(0));
        } catch (Exception e) {
            throw new UserException("Error while adding user", UserException.Type.USER_NOT_CREATED, e);
        }
    }

    public Optional<User> get(int userId) {
        try {
            List<User> user = jdbc.query("SELECT * FROM users WHERE user_id = ? LIMIT 1", new UserRowMapper(), userId);
            if (user.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(user.get(0));
        } catch (Exception e) {
            throw new UserException("Error while adding user", UserException.Type.USER_NOT_CREATED, e);
        }
    }

    public boolean exist(int userId) {
        return !jdbc.query("SELECT * FROM users WHERE user_id = ? LIMIT 1", new UserRowMapper(), userId).isEmpty();
    }
}
