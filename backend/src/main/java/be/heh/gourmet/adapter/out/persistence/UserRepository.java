package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.adapter.out.persistence.mapper.UserRowMapper;
import be.heh.gourmet.application.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void add(User user) {
        jdbc.update("INSERT INTO users (first_name,last_name, email, role) VALUES (?, ?, ?, ?)",
                user.firstname(), user.lastname(), user.email(), user.role().getValue());
    }

    public User findByEmail(String email) {
        return jdbc.query("SELECT * FROM users WHERE email = ? LIMIT 1", new UserRowMapper(), email).get(0);
    }

    public Optional<User> get(int userId) {
        List<User> user = jdbc.query("SELECT * FROM users WHERE user_id = ? LIMIT 1", new UserRowMapper(), userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(user.get(0));
    }

    public boolean exist(int userId) {
        return !jdbc.query("SELECT * FROM users WHERE user_id = ? LIMIT 1", new UserRowMapper(), userId).isEmpty();
    }
}
