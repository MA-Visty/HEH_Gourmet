package com.heh.gourmet.adapter.ou.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbc;

    public CartRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
