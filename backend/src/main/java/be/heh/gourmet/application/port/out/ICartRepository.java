package be.heh.gourmet.application.port.out;

import be.heh.gourmet.application.port.in.exception.CartException;
import be.heh.gourmet.application.domain.model.CartRow;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ICartRepository {
    void add(int userID, int productID, int quantity) throws CartException;

    void remove(int userID, int productID);

    void update(int userID, int productID, int quantity);

    void clear(int userID);

    boolean exists(int userID, int productID);

    void placeOrder(int userID, Date date);

    List<CartRow> get(int userID) throws CartException;

    Optional<CartRow> get(int userID, int productID);
}