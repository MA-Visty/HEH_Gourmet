package com.heh.gourmet.application.port.out;

import com.heh.gourmet.application.domain.model.Category;

public interface ICategoryRepository {

    void save(Category category);

    void remove(int ID);
}
