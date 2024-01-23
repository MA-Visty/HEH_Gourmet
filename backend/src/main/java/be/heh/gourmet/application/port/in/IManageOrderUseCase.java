package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface IManageOrderUseCase {
    void editStatus(int orderID, OrderStatus status);

    void editPrepareDate(int orderID, java.sql.Date date);

    List<OrderRow> list();

    List<OrderRow> list(OrderStatus status);

    List<OrderRow> list(int userID);

    List<OrderRow> list(int userID, OrderStatus status);

    Optional<OrderRow> get(int orderID);
}
