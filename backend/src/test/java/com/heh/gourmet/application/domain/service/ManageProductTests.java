package com.heh.gourmet.application.domain.service;

import com.heh.gourmet.application.domain.model.Category;
import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.port.out.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ManageProductTests {

    @Mock
    IProductRepository productRepositoryMock;

    Category category = new Category(1, "Sandwich");


    @Test
    public void testSaveProduct() {
        Product product = new Product(1, category, 2.3F);

        assertNotNull(productRepositoryMock);
        ManageProductImpl manageProductImpl = new ManageProductImpl(productRepositoryMock);
        manageProductImpl.createProduct(product);

        verify(productRepositoryMock).save(product);
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(1, category, 2.3F);

        assertNotNull(productRepositoryMock);
        ManageProductImpl manageProductImpl = new ManageProductImpl(productRepositoryMock);
        manageProductImpl.removeProduct(product.getID());

        verify(productRepositoryMock).remove(product.getID());
    }
}