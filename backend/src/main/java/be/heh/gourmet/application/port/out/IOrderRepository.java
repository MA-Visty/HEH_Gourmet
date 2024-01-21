package be.heh.gourmet.application.port.out;

import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {
    void editStatus(int orderID, String status);

    void editPrepareDate(int orderID, java.sql.Date date);

    List<OrderRow> list();

    List<OrderRow> list(OrderStatus status);

    List<OrderRow> list(int userID);

    List<OrderRow> list(int userID, OrderStatus status);

    Optional<OrderRow> get(int orderID);

    Optional<OrderRow> get(int orderID, int userID);
}
