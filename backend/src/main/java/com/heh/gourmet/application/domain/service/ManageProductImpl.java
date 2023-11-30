package com.heh.gourmet.application.domain.service;

import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.port.in.IManageProductUseCase;
import com.heh.gourmet.application.port.out.IProductRepository;

/**
 * The use case implementation to manage products
 * it allows to create and remove products from the product repository
 */
public class ManageProductImpl implements IManageProductUseCase {

    private final IProductRepository productRepository;

    public ManageProductImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void removeProduct(int ID) {
        productRepository.remove(ID);
    }
}