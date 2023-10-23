package com.heh.gourmet.application.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartTest {
    Cart cart;
    Category category = new Category(1, "Sandwich");

    @BeforeEach
    void setUp() {
        // reset cart before each test
        cart = new Cart();
    }

    @Test
    void computeTotalPrice() {
        // setup
        cart.products.put(1, new Product(1, category, 2.3F));
        cart.products.put(2, new Product(2, category, 5F, 5));
        // actual test
        assert cart.computeTotalPrice() == 27.3F;
    }

    @Test
    void addProduct() {
        cart.addProduct(new Product(1, category, 2.3F));
        assert cart.products.size() == 1;
        assert cart.products.get(1).getID() == 1;
    }

    @Test
    void removeProduct() {
        // setup
        cart.products.put(1, new Product(1, category, 2.3F));
        cart.products.put(2, new Product(2, category, 5F, 5));
        // actual test
        cart.removeProduct(1, 1);
        cart.removeProduct(2, 3);
        assert cart.products.size() == 1;
        assert cart.products.get(1) == null;
        assert cart.products.get(2).getQuantity() == 2;
    }

    @Test
    void deleteProduct() {
        // setup
        cart.products.put(1, new Product(1, category, 2.3F));
        cart.products.put(2, new Product(2, category, 5F, 5));
        // actual test
        cart.deleteProduct(1);
        assert cart.products.size() == 1;
        cart.deleteProduct(2);
        assert cart.products.isEmpty();
    }

    @Test
    void clear() {
        // setup
        cart.products.put(1, new Product(1, category, 2.3F));
        cart.products.put(2, new Product(2, category, 5F, 5));
        // actual test
        cart.clear();
        assert cart.products.isEmpty();
    }
}