package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.port.in.IManageCategoryUseCase;
import be.heh.gourmet.application.port.in.InputCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryAdapter implements IManageCategoryUseCase {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category add(InputCategory category) throws IllegalArgumentException {
        int ID = categoryRepository.add(category);
        return category.asCategory(ID);
    }

    @Override
    public void batchAdd(List<InputCategory> categories) throws IllegalArgumentException {
        categoryRepository.batchAdd(categories);
    }

    @Override
    public void update(int ID, InputCategory category) throws IllegalArgumentException {
        categoryRepository.update(ID, category);
    }

    @Override
    public void remove(int ID) throws IllegalArgumentException {
        categoryRepository.remove(ID);
    }

    @Override
    public void batchRemove(List<Integer> IDs) throws IllegalArgumentException {
        categoryRepository.batchRemove(IDs);
    }

    @Override
    public Optional<Category> get(int ID) throws IllegalArgumentException {
        return categoryRepository.get(ID);
    }

    @Override
    public Category getByProduct(int productID) throws IllegalArgumentException {
        return categoryRepository.getByProduct(productID);
    }

    @Override
    public List<Category> batchGet(List<Integer> IDs) throws IllegalArgumentException {
        return categoryRepository.batchGet(IDs);
    }

    @Override
    public List<Category> list() {
        return categoryRepository.getAll();
    }
}
