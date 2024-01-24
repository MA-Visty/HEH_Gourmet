package be.heh.gourmet.application.port.in;

import java.util.List;

public interface IManageFavUseCase {
    void add(int userID, int productID);

    void remove(int userID, int productID);

    List<Integer> list(int userID);
}
