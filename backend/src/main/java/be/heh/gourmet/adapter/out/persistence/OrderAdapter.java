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
    public void editStatus(int orderID, String status) {
        orderRepository.editStatus(orderID, status);
    }

    @Override
    public void editPrepareDate(int orderID, Date date) {
        orderRepository.editPrepareDate(orderID, date);
    }

    @Override
    public List<OrderRow> list() {
        throw new UnsupportedOperationException("Not implemented yet");

    }

    @Override
    public List<OrderRow> list(OrderStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<OrderRow> list(int userID) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<OrderRow> list(int userID, OrderStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<OrderRow> get(int orderID) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<OrderRow> get(int orderID, int userID) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
