package be.heh.gourmet.springboot.adapter.out;

import be.heh.gourmet.springboot.application.domain.model.Category;
import be.heh.gourmet.springboot.application.port.out.ICategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryAdapter implements ICategoryRepository {

    @Override
    public List<Category> loadsCategory() {
        return null;
    }
    @Override
    public void saveCategory(Category category) {}
    @Override
    public void updateCategory(Category category) {}
    @Override
    public void removeCategory(int ID) {}
}
