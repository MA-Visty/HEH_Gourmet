package com.heh.gourmet.application.domain.service;

import com.heh.gourmet.application.domain.model.Category;
import com.heh.gourmet.application.port.in.IManageCategoryUseCase;
import com.heh.gourmet.application.port.out.ICategoryRepository;

public class ManageCategoryImpl implements IManageCategoryUseCase {
    private final ICategoryRepository categoryRepository;

    public ManageCategoryImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void removeCategory(int ID) {
        categoryRepository.remove(ID);
    }
}