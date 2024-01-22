package be.heh.gourmet.backend.application.domain.service;

import be.heh.gourmet.backend.common.exception.NotFoundException;
import be.heh.gourmet.backend.application.domain.model.Product;
import be.heh.gourmet.backend.application.port.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        logger.debug("Product create action called");
        return productRepository.save(product);
    }

    public Product findOne(Long productId) {
        logger.debug("Product findById action called");
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product of id " +  productId + " not found."));
    }

    public List<Product> findAll() {
        logger.debug("Product findAll action called");
        return productRepository.findAll();
    }

    public Product update(Long productId, Product product) {
        logger.debug("Product update action called");
        Product foundProduct = findOne(productId);

        logger.debug("Product found");

        foundProduct.setType(product.getType());
        foundProduct.setName(product.getName());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setImageName(product.getImageName());
        foundProduct.setImageId(product.getImageId());
        foundProduct.setImageUrl(product.getImageUrl());
        foundProduct.setIngredients(product.getIngredients());
        productRepository.save(foundProduct);

        logger.debug("Product updated successfully");
        return foundProduct;
    }

    public void delete(Long productId) {
        logger.debug("Product delete action called");
        productRepository.deleteById(productId);
    }
}
