package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.domain.model.CartRow;

import java.sql.Date;
import java.util.List;

public interface IManageCartUseCase {
    void addProduct(int userID, int productID, int quantity) throws IllegalArgumentException, CartException;

    default void addProduct(int userID, int productID) throws CartException {
        addProduct(userID, productID, 1);
    }

    void removeProduct(int userID, int productID, int quantity) throws IllegalArgumentException, CartException;

    void completelyRemoveProduct(int userID, int productID) throws IllegalArgumentException, CartException;

    void editQuantity(int userID, int productID, int quantity) throws IllegalArgumentException;

    List<CartRow> get(int userID) throws CartException;

    void clear(int userID);

    void placeOrder(int userID, Date date);
}
