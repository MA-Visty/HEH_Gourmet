package be.heh.gourmet.application.domain.service;

import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageCartUseCase;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import be.heh.gourmet.application.port.out.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ManageCartImpl implements IManageCartUseCase {
    @Autowired
    @Qualifier("getCartRepository")
    private ICartRepository cartRepository;

    @Autowired
    @Qualifier("getManageProductUseCase")
    private IManageProductUseCase productManager;

    @Override
    public void addProduct(int userID, int productID, int quantity) throws IllegalArgumentException, CartException {
        Optional<Product> optionalProduct = productManager.get(productID);
        if (optionalProduct.isEmpty()) {
            throw new CartException("Product not found", CartException.Type.ASSOCIATED_PRODUCT_NOT_FOUND);
        }
        Product product = optionalProduct.get();
        Optional<CartRow> cartRow = cartRepository.get(userID, product.ID());
        if (cartRow.isPresent()) {
            cartRepository.update(userID, product.ID(), cartRow.get().quantity() + quantity);
        } else {
            cartRepository.add(userID, product.ID(), quantity);
        }
    }

    @Override
    public void removeProduct(int userID, int productID, int quantity) throws CartException {
        Optional<CartRow> cartRow = cartRepository.get(userID, productID);
        if (cartRow.isPresent()) {
            if (cartRow.get().quantity() <= quantity) {
                cartRepository.remove(userID, productID);
            } else {
                cartRepository.update(userID, productID, cartRow.get().quantity() - quantity);
            }
        } else {
            throw new CartException("Product not found in cart", CartException.Type.ASSOCIATED_PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void completelyRemoveProduct(int userID, int productID) throws CartException {
        Optional<CartRow> cartRow = cartRepository.get(userID, productID);
        if (cartRow.isPresent()) {
            cartRepository.remove(userID, productID);
        } else {
            throw new CartException("Product not found in cart", CartException.Type.ASSOCIATED_PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void editQuantity(int userID, int productID, int quantity) throws IllegalArgumentException {
        Optional<CartRow> cartRow = cartRepository.get(userID, productID);
        if (cartRow.isPresent()) {
            cartRepository.update(userID, productID, quantity);
        } else {
            throw new IllegalArgumentException("Product not found in cart");
        }
    }

    @Override
    public List<CartRow> get(int userID) throws CartException {
        return cartRepository.get(userID);
    }

    @Override
    public void clear(int userID) {
        cartRepository.clear(userID);
    }

    @Override
    public void placeOrder(int userID, Date date) {
        cartRepository.placeOrder(userID, date);
    }
}
