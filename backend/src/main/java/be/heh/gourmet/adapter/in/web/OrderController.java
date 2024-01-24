package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.application.domain.model.OrderStatus;
import be.heh.gourmet.application.port.in.IManageOrderUseCase;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import be.heh.gourmet.application.port.in.exception.OrderException;
import be.heh.gourmet.application.port.in.exception.ProductException;
import be.heh.gourmet.application.port.in.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@Slf4j
@RequestMapping("/api")
public class OrderController {
    @Autowired
    @Qualifier("getManageOrderUseCase")
    IManageOrderUseCase orderManager;

    @Autowired
    @Qualifier("getUserUseCase")
    IManageUserUseCase userManager;

    // only for admin or employee
    @GetMapping("/orders")
    public ResponseEntity<Object> listOrders() {
        try {
            return ResponseEntity.ok(orderManager.list());
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/pending")
    public ResponseEntity<Object> listPendingOrders() {
        try {
            return ResponseEntity.ok(orderManager.list(OrderStatus.PENDING));

        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/ready")
    public ResponseEntity<Object> listReadyOrders() {
        try {
            return ResponseEntity.ok(orderManager.list(OrderStatus.READY));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/delivered")
    public ResponseEntity<Object> listDeliveredOrders() {
        try {
            return ResponseEntity.ok(orderManager.list(OrderStatus.DELIVERED));

        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/cancelable")
    public ResponseEntity<Object> listCancelableOrders() {
        try {
            return ResponseEntity.ok(orderManager.list(OrderStatus.CANCELABLE));

        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/canceled")
    public ResponseEntity<Object> listCanceledOrders() {
        try {
            return ResponseEntity.ok(orderManager.list(OrderStatus.CANCELED));

        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    public record setOrderStatusDTO(OrderStatus status) {
    }

    @PostMapping("/orders/status/{orderID}")
    public ResponseEntity<Object> setOderStatus(@PathVariable int orderID, @RequestBody setOrderStatusDTO status) {
        try {
            orderManager.editStatus(orderID, status.status);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    // accessible from user

    @GetMapping("/orders/{userID}")
    public ResponseEntity<Object> listOrders(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(orderManager.list(userID));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/{userID}/pending")
    public ResponseEntity<Object> listPendingOrders(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(orderManager.list(userID, OrderStatus.PENDING));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/{userID}/ready")
    public ResponseEntity<Object> listReadyOrders(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(orderManager.list(userID, OrderStatus.READY));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/{userID}/delivered")
    public ResponseEntity<Object> listDeliveredOrders(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(orderManager.list(userID, OrderStatus.DELIVERED));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/{userID}/cancelable")
    public ResponseEntity<Object> listCancelableOrders(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(orderManager.list(userID, OrderStatus.CANCELABLE));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/orders/{userID}/canceled")
    public ResponseEntity<Object> listCanceledOrders(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(orderManager.list(userID, OrderStatus.CANCELED));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/orders/{userID}/cancel/{orderID}")
    public ResponseEntity<Object> cancelOrder(@PathVariable int userID, @PathVariable int orderID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            orderManager.editStatus(orderID, OrderStatus.CANCELED);
            return ResponseEntity.ok().build();
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/orders/{userID}/date/{orderID}")
    public ResponseEntity<Object> setPrepareDate(@PathVariable int userID, @PathVariable int orderID, @RequestBody Date date) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            orderManager.editPrepareDate(orderID, date);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (OrderException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}
