package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.domain.model.Product;

import java.util.List;

public interface IManageCartUseCase {
    void addProduct(String userID, Product product, int quantity) throws IllegalArgumentException;

    void removeProduct(String userID, int productID, int quantity) throws IllegalArgumentException;

    void editQuantity(String userID, int productID, int quantity) throws IllegalArgumentException;

    List<CartRow> list(String userID);

    void clear(String userID);

    void checkout(String userID);
}
