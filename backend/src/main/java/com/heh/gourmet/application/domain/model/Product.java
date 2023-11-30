package com.heh.gourmet.application.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Product class
 */
@Getter
public class Product {
    @Id
    private final int ID;
    @Setter
    int quantity;
    private final Category category;
    private final float price;

    /**
     * Constructor
     *
     * @param ID       product ID
     * @param category product type
     * @param price    product price
     */
    public Product(int ID, Category category, float price) {
        this(ID, category, price, 1);
    }

    /**
     * Constructor
     *
     * @param ID       product ID
     * @param category product type
     * @param price    product price
     * @param quantity product quantity
     */
    public Product(int ID, Category category, float price, int quantity) {
        this.ID = ID;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Compute the price of the product
     *
     * @return the price of the product
     */
    public float computePrice() {
        return price * quantity;
    }
}
