package com.heh.gourmet.application.port.out;

import com.heh.gourmet.application.domain.model.Product;

import java.util.Optional;

/**
 * The repository to manage products
 * it allows to save and remove products from the product repository
 */
public interface IProductRepository {
    /**
     * Load a product from the product repository
     *
     * @param ID the ID of the product to load
     * @return the product with the given ID
     */
    Optional<Product> load(int ID);

    /**
     * Save a product in the product repository
     *
     * @param product the product to save
     */
    void save(Product product);

    /**
     * Remove a product from the product repository
     *
     * @param ID the ID of the product to remove
     */
    void remove(int ID);
}
