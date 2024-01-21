package be.heh.gourmet.adapter.out.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class OrderRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }


    public void placeOrder(int userID, Date date) {
        Date now = new Date(System.currentTimeMillis());
        try {
            jdbc.update("INSERT INTO orders (user_id,order_date, prepare_date) VALUES (?,?, ?)", userID, now, date);
        } catch (DataAccessException e) {
            Logger logger = LoggerFactory.getLogger(OrderRepository.class);
            logger.error("Error while placing order", e);
        }
    }
}
