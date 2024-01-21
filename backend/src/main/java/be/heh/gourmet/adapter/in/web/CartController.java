package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.adapter.in.web.exeption.CustomException;
import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.adapter.out.payment.exception.PaymentException;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.port.in.IManageCartUseCase;
import be.heh.gourmet.application.port.in.IPaymentUseCase;
import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.port.in.exception.OrderException;
import be.heh.gourmet.application.port.in.exception.ProductException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// TODO : use @PreAuthorize("@userAuthManager.ID(#userID) == principal.id") to check if the user is the owner of the cart
// principal.id is the id given by the auth provider (here, keycloak)
// userID is the id given by the application
// userAuthManager make the link between the two

@RestController
@Slf4j
@RequestMapping("/api")
public class CartController {
    @Autowired
    @Qualifier("getManageCartUseCase")
    private IManageCartUseCase cartManager;

    @Autowired
    @Qualifier("getPaymentUseCase")
    private IPaymentUseCase paymentManager;

    @GetMapping("/cart/{userID}")
    public ResponseEntity<Object> getCart(@PathVariable int userID) {
        try {
            List<CartRow> cart = cartManager.get(userID);
            if (cart == null) {
                cart = List.of();
            }
            return ResponseEntity.ok(cart);
        } catch (CartException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting cart", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/cart/{userID}")
    public ResponseEntity<Object> addToCart(@PathVariable int userID, @RequestParam @NotNull int productID, @RequestParam Optional<Integer> quantity) {
        try {
            if (quantity.isEmpty()) {
                cartManager.addProduct(userID, productID);
            } else {
                int quantityValue = quantity.get();
                if (quantityValue <= 0) {
                    throw new CartException("Quantity must be positive", CartException.Type.INVALID_QUANTITY);
                }
                cartManager.addProduct(userID, productID, quantityValue);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CartException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while adding product to cart", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @DeleteMapping("/cart/{userID}")
    public ResponseEntity<Object> removeFromCart(@PathVariable int userID, @RequestParam int productID, @RequestParam Optional<Integer> quantity) {
        try {
            if (quantity.isEmpty()) {
                cartManager.completelyRemoveProduct(userID, productID);
            } else {
                int quantityValue = quantity.get();
                if (quantityValue <= 0) {
                    throw new CartException("Quantity must be positive", CartException.Type.INVALID_QUANTITY);
                }
                cartManager.removeProduct(userID, productID, quantityValue);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CartException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while removing product from cart", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/cart/{userID}/checkout")
    @CrossOrigin("*")
    public ResponseEntity<Object> checkout(@PathVariable int userID, @RequestBody @NotNull Map<String, Object> params) {
        try {
            List<CartRow> cart = cartManager.get(userID);
            if (cart == null || cart.isEmpty()) {
                throw new OrderException("Cart is empty", OrderException.Type.CART_IS_EMPTY);
            }

            if (!params.containsKey("targetDate") || params.get("targetDate") == null) {
                CustomException e = new CustomException("Missing targetDate parameter", HttpStatus.BAD_REQUEST);
                return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
            }
            Date targetDate = Date.valueOf(params.get("targetDate").toString());
            if (targetDate.before(new Date(System.currentTimeMillis()))) {
                throw new OrderException("Target date must be in the future", OrderException.Type.PREPARE_DATE_CANNOT_BE_IN_THE_PAST);
            }

            // this will throw an exception if the payment fails (see src/main/java/be/heh/gourmet/adapter/out/payment/StripePaymentAdapter.java)
            // at this stage the cart is not modified and the order is not placed
            // only if this call succeeds, the cart is modified and the order is placed
            // TODO : paymentManager.charge(userID, cart, params);
            cartManager.placeOrder(userID, targetDate);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            OrderException err = new OrderException("Invalid targetDate format", OrderException.Type.INVALID_TARGET_DATE_FORMAT, e);
            return ResponseEntity.status(err.httpStatus()).body(err.toResponse());
        } catch (PaymentException e) {
            return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
        } catch (CartException e) {
            return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
        } catch (OrderException e) {
            return ResponseEntity.status(e.httpStatus()).body(e.toResponse());
        } catch (Exception e) {
            log.error("Error while charging user", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}
