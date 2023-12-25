package be.heh.gourmet.springboot.application.port.out;

import be.heh.gourmet.springboot.application.domain.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository {
    List<Category> loadsCategory();
    void saveCategory(Category category);
    void updateCategory(Category category);
    void removeCategory(int ID);
}
