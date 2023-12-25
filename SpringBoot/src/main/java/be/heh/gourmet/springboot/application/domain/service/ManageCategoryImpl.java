package be.heh.gourmet.springboot.application.domain.service;

import be.heh.gourmet.springboot.application.domain.model.Category;
import be.heh.gourmet.springboot.application.port.in.IManageCategoryUseCase;
import be.heh.gourmet.springboot.application.port.out.ICategoryRepository;

public class ManageCategoryImpl implements IManageCategoryUseCase {

    private final ICategoryRepository categoryRepository;

    public ManageCategoryImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.saveCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    @Override
    public void removeCategory(int ID) {
        categoryRepository.removeCategory(ID);
    }
}
