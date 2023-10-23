package com.heh.gourmet.application.port.in;

import com.heh.gourmet.application.domain.model.Product;

import java.util.ArrayList;

/**
 * The use case to manage user's cart
 * it allows to create and remove products from the user's cart
 * it also allows to get the products in the user's cart and to clear it
 */
public interface IManageCartUseCase {

    /**
     * Add a product to the cart
     *
     * @param ID       the ID of the product to add
     * @param quantity the quantity of the product to add
     *                 if the product is already in the cart, the quantity will be added to the existing quantity
     * @throws IllegalArgumentException if the product doesn't exist
     */
    void addProduct(int ID, int quantity) throws IllegalArgumentException;

    /**
     * Add a product to the cart
     *
     * @param ID the ID of the product to add
     * @throws IllegalArgumentException if the product doesn't exist
     */
    default void addProduct(int ID) throws IllegalArgumentException {
        this.addProduct(ID, 1);
    }

    /**
     * Remove a product from the cart by a certain amount
     *
     * @param ID     the ID of the product to remove
     * @param amount the amount of the product to remove
     */
    void removeProduct(int ID, int amount);

    /**
     * Completely remove a product from the cart
     *
     * @param ID the ID of the product to delete from the cart
     */
    void deleteProduct(int ID);

    /**
     * Get the products in the cart
     *
     * @return the products in the cart
     */
    ArrayList<Product> getProducts();

    /**
     * clear the cart
     */
    void clear();
}
