package be.heh.gourmet.application.domain.service;

import be.heh.gourmet.application.domain.model.OrderRow;
import be.heh.gourmet.application.domain.model.OrderStatus;
import be.heh.gourmet.application.port.in.IManageOrderUseCase;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import be.heh.gourmet.application.port.in.exception.OrderException;
import be.heh.gourmet.application.port.out.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class ManagerOrderImpl implements IManageOrderUseCase {
    @Autowired
    @Qualifier("getOrderRepository")
    IOrderRepository orderManager;

    @Override
    public void editStatus(int orderID, OrderStatus status) {
        Optional<OrderRow> order = orderManager.get(orderID);
        if (order.isEmpty()) {
            throw new OrderException("Order not found", OrderException.Type.ORDER_NOT_FOUND);
        }
        switch (order.get().status()) {
            case CANCELED -> {
                throw new OrderException("Order is canceled", OrderException.Type.ORDER_ALREADY_CANCELED);
            }
            case CANCELABLE -> {
                if (status != OrderStatus.CANCELED && status != OrderStatus.PENDING) {
                    throw new OrderException("Order is cancelable", OrderException.Type.INVALID_STATUS);
                }
            }
            case PENDING -> {
                if (status != OrderStatus.READY && status != OrderStatus.CANCELED) {
                    throw new OrderException("Order is pending", OrderException.Type.INVALID_STATUS);
                }
            }
            case READY -> {
                if (status != OrderStatus.DELIVERED) {
                    throw new OrderException("Order is ready", OrderException.Type.INVALID_STATUS);
                }
            }
            case DELIVERED -> {
                throw new OrderException("Order is delivered", OrderException.Type.ORDER_ALREADY_DELIVERED);
            }
        }
        orderManager.editStatus(orderID, status);
    }

    @Override
    public void editPrepareDate(int orderID, Date date) {
        orderManager.editPrepareDate(orderID, date);
    }

    @Override
    public List<OrderRow> list() {
        return orderManager.list();
    }

    @Override
    public List<OrderRow> list(OrderStatus status) {
        return orderManager.list(status);
    }

    @Override
    public List<OrderRow> list(int userID) {
        return orderManager.list(userID);
    }

    @Override
    public List<OrderRow> list(int userID, OrderStatus status) {
        return orderManager.list(userID, status);
    }

    @Override
    public Optional<OrderRow> get(int orderID) {
        return orderManager.get(orderID);
    }
}