package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;
import be.heh.gourmet.application.port.out.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
public class OrderAdapter implements IOrderRepository {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void editStatus(int orderID, OrderStatus status) {
        orderRepository.editStatus(orderID, status);
    }

    @Override
    public void editPrepareDate(int orderID, Date date) {
        orderRepository.editPrepareDate(orderID, date);
    }

    @Override
    public List<OrderRow> list() {
        return orderRepository.list();
    }

    @Override
    public List<OrderRow> list(OrderStatus status) {
        return orderRepository.list(status);
    }

    @Override
    public List<OrderRow> list(int userID) {
        return orderRepository.list(userID);
    }

    @Override
    public List<OrderRow> list(int userID, OrderStatus status) {
        return orderRepository.list(userID, status);
    }

    @Override
    public Optional<OrderRow> get(int orderID) {
        return orderRepository.get(orderID);
    }

    @Override
    public Optional<OrderRow> get(int orderID, int userID) {
        return orderRepository.get(orderID, userID);
    }
}
