package com.heh.gourmet.application.port.in;

import com.heh.gourmet.application.domain.model.Category;

public interface IManageCategoryUseCase {

    void createCategory(Category category);

    void removeCategory(int ID);
}
