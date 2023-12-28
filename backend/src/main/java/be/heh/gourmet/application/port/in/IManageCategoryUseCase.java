package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.Category;

import java.util.List;

public interface IManageCategoryUseCase {
    Category add(InputCategory category) throws IllegalArgumentException;

    void batchAdd(List<InputCategory> categories) throws IllegalArgumentException;

    void update(int ID, InputCategory category) throws IllegalArgumentException;

    void remove(int ID) throws IllegalArgumentException;

    void batchRemove(List<Integer> IDs) throws IllegalArgumentException;

    Category get(int ID) throws IllegalArgumentException;

    Category getByProduct(int productID) throws IllegalArgumentException;

    List<Category> batchGet(List<Integer> IDs) throws IllegalArgumentException;

    List<Category> list();
}
