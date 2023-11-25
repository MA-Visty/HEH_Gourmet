package com.heh.gourmet.application.domain.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Cart class
 */
public class Cart {
    /**
     * user ID
     */
    @Id
    public final int ID;
    /**
     * products in the cart identified by their ID
     */
    HashMap<Integer, Product> products;

    /**
     * Constructor
     *
     * @param products products in the cart
     */
    public Cart(@NotNull int ID, @NotNull HashMap<Integer, Product> products) {
        this.products = products;
        this.ID = ID;
    }

    /**
     * Constructor
     */
    public Cart(@NotNull int ID) {
        this(ID, new HashMap<>());
    }

    /**
     * Get the total price of the cart
     *
     * @return the total price of the cart
     */
    public float computeTotalPrice() {
        float totalPrice = 0;
        for (Product product : products.values()) {
            totalPrice += product.computePrice();
        }
        return totalPrice;
    }

    /**
     * Add a product to the cart
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        int ID = product.getID();
        if (products.containsKey(ID)) {
            Product old_product = products.get(ID);
            old_product.setQuantity(old_product.getQuantity() + product.getQuantity());
        } else {
            products.put(ID, product);
        }
    }

    /**
     * Remove a product from the cart by a certain amount
     *
     * @param ID     the ID of the product
     * @param amount the amount to remove
     */
    public void removeProduct(int ID, int amount) {
        if (products.containsKey(ID)) {
            int oldAmount = products.get(ID).getQuantity();
            if (oldAmount <= amount) {
                products.remove(ID);
                return;
            }
            products.get(ID).setQuantity(oldAmount - amount);
        }
    }


    /**
     * Completely remove a product from the cart
     *
     * @param ID the ID of the product
     */
    public void deleteProduct(int ID) {
        products.remove(ID);
    }

    /**
     * Get the products in the cart
     *
     * @return the products in the cart
     */
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    /**
     * Clear the cart from all products
     */
    public void clear() {
        this.products.clear();
    }
}
