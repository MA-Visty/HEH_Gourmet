package com.heh.gourmet.application.domain.service;

import com.heh.gourmet.application.domain.model.Cart;
import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.port.in.IManageCartUseCase;
import com.heh.gourmet.application.port.out.ICartRepository;
import com.heh.gourmet.application.port.out.IProductRepository;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The use case implementation to manage user's cart
 * it allows to create and remove products from the user's cart
 * it also allows to get the products in the user's cart and to clear it
 */
public class ManageCartImpl implements IManageCartUseCase {
    Cart cart;
    @NotNull ICartRepository cartHandel;
    @NotNull IProductRepository productRepository;

    public ManageCartImpl(@NotNull ICartRepository cart, @NotNull IProductRepository productRepository) {
        this.productRepository = productRepository;
        this.cartHandel = cart;
    }

    @Override
    public void init(@NotNull int ID) {
        Optional<Cart> cartOptional = this.cartHandel.load(ID);
        this.cart = cartOptional.orElseGet(() -> new Cart(ID));
    }

    @Override
    public void addProduct(int ID, int quantity) throws IllegalArgumentException {
        if (this.cart == null) {
            throw new NullPointerException("the cart has not been instanced yet");
        }
        Optional<Product> optionalProduct = productRepository.load(ID);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("The product with the ID " + ID + " doesn't exist");
        }
        cart.addProduct(optionalProduct.get());
        cartHandel.save(cart);
    }

    @Override
    public void removeProduct(int ID, int quantity) {
        if (this.cart == null) {
            throw new NullPointerException("the cart has not been instanced yet");
        }
        cart.removeProduct(ID, quantity);
        cartHandel.save(cart);
    }

    @Override
    public void deleteProduct(int ID) {
        if (this.cart == null) {
            throw new NullPointerException("the cart has not been instanced yet");
        }
        cart.deleteProduct(ID);
        cartHandel.save(cart);
    }

    @Override
    public ArrayList<Product> getProducts() {
        if (this.cart == null) {
            throw new NullPointerException("the cart has not been instanced yet");
        }
        return cart.getProducts();
    }

    @Override
    public void clear() {
        if (this.cart == null) {
            throw new NullPointerException("the cart has not been instanced yet");
        }
        cartHandel.clear(cart.ID);
    }
}
