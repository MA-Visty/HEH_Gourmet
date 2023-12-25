package be.heh.gourmet.springboot.controller;

import be.heh.gourmet.springboot.application.domain.model.Product;
import be.heh.gourmet.springboot.application.port.out.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductRepository productRepository;
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.loadsProduct();
    }
}
