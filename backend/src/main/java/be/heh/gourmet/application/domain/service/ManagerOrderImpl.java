package be.heh.gourmet.application.domain.service;

import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;
import be.heh.gourmet.application.port.in.IManageOrderUseCase;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class ManagerOrderImpl implements IManageOrderUseCase {
    @Override
    public void editStatus(int orderID, OrderStatus status) {

    }

    @Override
    public void editPrepareDate(int orderID, Date date) {

    }

    @Override
    public List<OrderRow> list() {
        return null;
    }

    @Override
    public List<OrderRow> list(OrderStatus status) {
        return null;
    }

    @Override
    public List<OrderRow> list(int userID) {
        return null;
    }

    @Override
    public List<OrderRow> list(int userID, OrderStatus status) {
        return null;
    }

    @Override
    public Optional<OrderRow> get(int orderID) {
        return Optional.empty();
    }
}
