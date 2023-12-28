package be.heh.gourmet.application.domain.service;

import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageCartUseCase;
import be.heh.gourmet.application.port.out.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageCartImpl implements IManageCartUseCase {
    @Autowired
    @Qualifier("getCartRepository")
    private ICartRepository cartRepository;

    @Override
    public void addProduct(String userID, Product product, int quantity) throws IllegalArgumentException {

    }

    @Override
    public void removeProduct(String userID, int productID, int quantity) throws IllegalArgumentException {

    }

    @Override
    public void editQuantity(String userID, int productID, int quantity) throws IllegalArgumentException {

    }

    @Override
    public List<CartRow> list(String userID) {
        return null;
    }

    @Override
    public void clear(String userID) {

    }

    @Override
    public void checkout(String userID) {

    }
}
