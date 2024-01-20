package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.port.in.exception.OrderException;
import be.heh.gourmet.application.port.out.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CartAdapter implements ICartRepository {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void add(int userID, int productID, int quantity) throws CartException {
        cartRepository.add(userID, productID, quantity);
    }

    @Override
    public void remove(int userID, int productID) {
        cartRepository.remove(userID, productID);
    }

    @Override
    public void update(int userID, int productID, int quantity) {
        cartRepository.update(userID, productID, quantity);
    }

    @Override
    public void clear(int userID) {
        cartRepository.clear(userID);
    }

    @Override
    public boolean exists(int userID, int productID) {
        return cartRepository.exists(userID, productID);
    }

    @Override
    public void placeOrder(int userID, Date date) {
        // check if cart is empty
        if (isCartEmpty(userID)) {
            throw new OrderException("Cart is empty", OrderException.Type.CART_IS_EMPTY);
        }
        orderRepository.placeOrder(userID, date);
    }

    @Override
    public List<CartRow> get(int userID) throws CartException {
        if (!userRepository.exist(userID)) {
            throw new CartException("User not found", CartException.Type.ASSOCIATED_USER_NOT_FOUND);
        }
        return cartRepository.list(userID);
    }

    @Override
    public Optional<CartRow> get(int userID, int productID) {
        return cartRepository.get(userID, productID);
    }

    private boolean isCartEmpty(int userID) {
        return cartRepository.list(userID).isEmpty();
    }
}
