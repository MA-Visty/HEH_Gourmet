package be.heh.gourmet.backend.controller;

import be.heh.gourmet.backend.model.Order;
import be.heh.gourmet.backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Order order) {
        logger.debug("Order create controller called");
        orderService.create(order);
        return new ResponseEntity<>("Order create successfully ! ", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        logger.debug("Order findAll controller called");
        List<Order> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOne(@PathVariable Long id) {
        logger.debug("Order findOne controller called with id: {}", id);
        Order order = orderService.findOne(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
        logger.debug("Order update controller called with id: {}", id);
        Order orderRes = orderService.update(id, order);
        return new ResponseEntity<>(orderRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.debug("Order delete controller called with id: {}", id);
        orderService.delete(id);
        return new ResponseEntity<>("Order deleted successfully !", HttpStatus.OK);
    }
}
