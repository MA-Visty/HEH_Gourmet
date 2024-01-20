package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.port.in.exception.OrderException;
import be.heh.gourmet.application.port.out.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CartAdapter implements ICartRepository {
    @Autowired
    CartRepository cartRepository;

    @Override
    public void add(String userID, int productID, int quantity) {
        cartRepository.add(userID, productID, quantity);
    }

    @Override
    public void remove(String userID, int productID) {
        cartRepository.remove(userID, productID);
    }

    @Override
    public void update(String userID, int productID, int quantity) {
        cartRepository.update(userID, productID, quantity);
    }

    @Override
    public void clear(String userID) {
        cartRepository.clear(userID);
    }

    @Override
    public boolean exists(String userID, int productID) {
        return cartRepository.exists(userID, productID);
    }

    @Override
    public void placeOrder(String userID, Date date) {
        // TODO : use orderRepository once it's implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CartRow> list(String userID) {
        return cartRepository.list(userID);
    }

    @Override
    public Optional<CartRow> get(String userID, int productID) {
        return cartRepository.get(userID, productID);
    }
}
