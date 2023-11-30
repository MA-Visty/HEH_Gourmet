package com.heh.gourmet.application.domain.service;

import com.heh.gourmet.application.domain.model.Cart;
import com.heh.gourmet.application.domain.model.Category;
import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.port.out.ICartRepository;
import com.heh.gourmet.application.port.out.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManageCartTests {
    @Mock
    ICartRepository cartRepo;
    @Mock
    IProductRepository productRepository;

    Cart cart = new Cart(1);
    Category category = new Category(1, "Sandwich");

    @Test
    public void testAddProductToCart() {
        ManageCartImpl manageCart = new ManageCartImpl(cartRepo, productRepository);
        manageCart.init(1);
        when(productRepository.load(1)).thenReturn(java.util.Optional.of(new Product(1, category, 2.3F)));
        manageCart.addProduct(1);
        assert (manageCart.cart.getProducts().size() == 1);
        verify(cartRepo).save(manageCart.cart);
    }

    @Test
    public void testRemoveProductFromCart() {
        ManageCartImpl manageCart = new ManageCartImpl(cartRepo, productRepository);
        manageCart.init(1);
        // Add a product to the cart bypassing the ManageCartImpl.addProduct method
        manageCart.cart.addProduct(new Product(1, category, 2.3F));
        manageCart.removeProduct(1, 1);
        assert (manageCart.cart.getProducts().isEmpty());
        verify(cartRepo).save(manageCart.cart);
    }

    @Test
    public void testClearCart() {
        ManageCartImpl manageCart = new ManageCartImpl(cartRepo, productRepository);
        manageCart.init(1);
        manageCart.clear();
        verify(cartRepo).clear(1);
    }
}
