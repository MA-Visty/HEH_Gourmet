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
    @NotNull Cart cart;
    @NotNull ICartRepository cartHandel;
    @NotNull IProductRepository productRepository;

    /**
     * Constructor
     *
     * @param ID                the ID of the cart to manage
     *                          if the cart doesn't exist, it will be created
     *                          if the cart exists, it will be loaded
     * @param cart              the cart repository to use
     * @param productRepository the product repository to use
     */
    public ManageCartImpl(@NotNull int ID, @NotNull ICartRepository cart, @NotNull IProductRepository productRepository) {
        this.productRepository = productRepository;
        Optional<Cart> cartOptional = cart.load(ID);
        if (cartOptional.isEmpty()) {
            cartHandel = cart;
            this.cart = new Cart(ID);
        } else {
            this.cart = cartOptional.get();
            cartHandel = cart;
        }
    }

    /**
     * Add a product to the cart
     *
     * @param ID       the ID of the product to add
     * @param quantity the quantity of the product to add
     *                 if the product is already in the cart, the quantity will be added to the existing quantity
     * @throws IllegalArgumentException if the product doesn't exist
     */
    @Override
    public void addProduct(int ID, int quantity) throws IllegalArgumentException {
        Optional<Product> optionalProduct = productRepository.load(ID);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("The product with the ID " + ID + " doesn't exist");
        }
        cart.addProduct(optionalProduct.get());
        cartHandel.save(cart);
    }

    /**
     * Reduce the quantity of a product in the cart removing it if the resulting quantity is 0 or less
     *
     * @param ID       the ID of the product to remove
     * @param quantity the quantity of the product to remove
     */
    @Override
    public void removeProduct(int ID, int quantity) {
        cart.removeProduct(ID, quantity);
        cartHandel.save(cart);
    }

    /**
     * Delete a product from the cart
     *
     * @param ID the ID of the product to delete from the cart
     */
    @Override
    public void deleteProduct(int ID) {
        cart.deleteProduct(ID);
        cartHandel.save(cart);
    }

    /**
     * Get a list of the products in the cart
     *
     * @return the products in the cart
     */
    @Override
    public ArrayList<Product> getProducts() {
        return cart.getProducts();
    }

    /**
     * clear the cart
     */
    @Override
    public void clear() {
        cartHandel.clear(cart.ID);
    }
}
