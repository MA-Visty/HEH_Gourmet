package com.heh.gourmet.application.domain.service;

import com.heh.gourmet.application.domain.model.Category;
import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.port.out.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ManageCategoryTests {
    @Mock
    ICategoryRepository categoryRepositoryMock;

    @Test
    public void testSaveCategory() {
        Category category = new Category(1, "Sandwich");

        assertNotNull(categoryRepositoryMock);
        ManageCategoryImpl manageCategoryImpl = new ManageCategoryImpl(categoryRepositoryMock);
        manageCategoryImpl.createCategory(category);

        verify(categoryRepositoryMock).save(category);
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category(1, "Sandwich");

        assertNotNull(categoryRepositoryMock);
        ManageCategoryImpl manageCategoryImpl = new ManageCategoryImpl(categoryRepositoryMock);
        manageCategoryImpl.removeCategory(category.getID());

        verify(categoryRepositoryMock).remove(category.getID());
    }
}
