package be.heh.gourmet.backend.application.domain.service;

import be.heh.gourmet.backend.common.exception.NotFoundException;
import be.heh.gourmet.backend.application.domain.model.Order;
import be.heh.gourmet.backend.application.port.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order) {
        logger.debug("Order create action called");
        return orderRepository.save(order);
    }

    public Order findOne(Long orderId) {
        logger.debug("Order findById action called");
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order of id " +  orderId + " not found."));
    }

    public List<Order> findAll() {
        logger.debug("Order findAll action called");
        return orderRepository.findAll();
    }

    public Order update(Long orderId, Order order) {
        logger.debug("Order update action called");
        Order foundOrder = findOne(orderId);

        logger.debug("Order found");

        foundOrder.setUserId(order.getUserId());
        foundOrder.setProducts(order.getProducts());
        foundOrder.setPrice(order.getPrice());
        foundOrder.setState(order.getState());
        foundOrder.setDate(order.getDate());
        orderRepository.save(foundOrder);

        logger.debug("Order updated successfully");
        return foundOrder;
    }

    public void delete(Long orderId) {
        logger.debug("Order delete action called");
        orderRepository.deleteById(orderId);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllOrdersAtMidnight() {
        orderRepository.deleteAllOrders();
    }
}
