package com.heh.gourmet.application.port.in;

import com.heh.gourmet.application.domain.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface IManageCategoryUseCase {

    void createCategory(Category category);

    void removeCategory(int ID);
}
