package be.heh.gourmet.application.domain.service;

import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageCartUseCase;
import be.heh.gourmet.application.port.out.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManageCartImpl implements IManageCartUseCase {
    @Autowired
    @Qualifier("getCartRepository")
    private ICartRepository cartRepository;

    @Override
    public void addProduct(String userID, Product product, int quantity) throws IllegalArgumentException {
        Optional<Product> optionalProduct = productManager.get(productID);
        if (optionalProduct.isEmpty()) {
            throw new ProductException("Product not found", ProductException.Type.PRODUCT_NOT_FOUND);
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
    public void removeProduct(String userID, int productID, int quantity) throws IllegalArgumentException {
        Optional<CartRow> cartRow = cartRepository.get(userID, productID);
        if (cartRow.isPresent()) {
            if (cartRow.get().quantity() <= quantity) {
                cartRepository.remove(userID, productID);
            } else {
                cartRepository.update(userID, productID, cartRow.get().quantity() - quantity);
            }
        } else {
            throw new IllegalArgumentException("Product not found in cart");
        }
    }

    @Override
    public void editQuantity(String userID, int productID, int quantity) throws IllegalArgumentException {
        Optional<CartRow> cartRow = cartRepository.get(userID, productID);
        if (cartRow.isPresent()) {
            cartRepository.update(userID, productID, quantity);
        } else {
            throw new IllegalArgumentException("Product not found in cart");
        }
    }

    @Override
    public List<CartRow> list(String userID) {
        return cartRepository.list(userID);
    }

    @Override
    public void clear(String userID) {
        cartRepository.clear(userID);
    }

    @Override
    public void checkout(String userID) {
        // TODO :  method signature is not final
    }
}
