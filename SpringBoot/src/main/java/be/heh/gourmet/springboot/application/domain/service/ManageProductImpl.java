package be.heh.gourmet.springboot.application.domain.service;

import be.heh.gourmet.springboot.application.domain.model.Product;
import be.heh.gourmet.springboot.application.port.in.IManageProductUseCase;
import be.heh.gourmet.springboot.application.port.out.IProductRepository;

public class ManageProductImpl implements IManageProductUseCase {

    private final IProductRepository productRepository;

    public ManageProductImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(Product product) {
        productRepository.saveProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    @Override
    public void removeProduct(int ID) {
        productRepository.removeProduct(ID);
    }
}