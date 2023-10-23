package com.heh.gourmet.application.port.in;

import com.heh.gourmet.application.domain.model.Product;

/**
 * The use case to manage products
 * it allows to create and remove products from the product repository
 */
public interface IManageProductUseCase {

    /**
     * Create a product in the product repository
     *
     * @param product the product to create
     */
    void createProduct(Product product);

    /**
     * Remove a product from the product repository
     *
     * @param ID the ID of the product to remove
     */
    void removeProduct(int ID);
}
