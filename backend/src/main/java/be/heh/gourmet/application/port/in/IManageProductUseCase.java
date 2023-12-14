package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.Product;

import java.util.List;

public interface IManageProductUseCase {
    Product add(InputProduct product) throws IllegalArgumentException;

    void update(int ID , InputProduct product) throws IllegalArgumentException;

    void remove(int ID) throws IllegalArgumentException;

    Product get(int ID) throws IllegalArgumentException;

    List<Product> list();

    List<Product> listByCategory(int categoryID);
}
