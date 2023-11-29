package com.heh.gourmet.adapter.ou.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public class ProfileRepository {
    private final JdbcTemplate jdbc;

    public ProfileRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    Optional<Integer> login(String login, String hashedPassword) {

        // if login is in table and password match
        //      return user id
        // else
        return Optional.empty();
    }
}
