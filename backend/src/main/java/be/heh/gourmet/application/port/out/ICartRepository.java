package be.heh.gourmet.application.port.out;

import be.heh.gourmet.application.domain.model.CartRow;

import java.util.Date;
import java.util.List;

public interface ICartRepository {
    void add(String userID, int productID, int quantity);

    void remove(String userID, int productID);

    void update(String userID, int productID, int quantity);

    void clear(String userID);

    boolean exists(String userID, int productID);

    void placeOrder(String userID, Date date);

    List<CartRow> list(String userID);
}