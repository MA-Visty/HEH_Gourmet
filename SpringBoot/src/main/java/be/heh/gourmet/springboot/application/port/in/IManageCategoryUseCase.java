package be.heh.gourmet.springboot.application.port.in;

import be.heh.gourmet.springboot.application.domain.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface IManageCategoryUseCase {

    void createCategory(Category category);
    void updateCategory(Category category);
    void removeCategory(int ID);
}
