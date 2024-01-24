package be.heh.gourmet.adapter.out.persistence.mapper;

import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<OrderRow> {

    @Override
    public OrderRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderRow(
                rs.getInt("order_id"),
                rs.getDate("order_date"),
                rs.getDate("prepare_date"),
                rs.getFloat("total"),
                OrderStatus.of(rs.getInt("status")));
    }
}
