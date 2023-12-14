package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.port.in.InputProduct;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class ProductController {
    @Autowired
    @Qualifier("getManageProductUseCase")
    IManageProductUseCase productManager;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productManager.list();
        if (products == null) {
            return new ResponseEntity<>(null, null, 404);
        }
        return new ResponseEntity<>(products, null, 200);
    }

    @GetMapping("/products/{category_id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable int category_id) {
        List<Product> products = productManager.listByCategory(category_id);
        if (products == null) {
            return new ResponseEntity<>(null, null, 404);
        }
        return new ResponseEntity<>(products, null, 200);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@Validated @RequestBody InputProduct product) {
        Product response = productManager.add(product);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = productManager.get(id);
        if (product == null) {
            return new ResponseEntity<>(null, null, 404);
        }
        return new ResponseEntity<>(product, null, 200);
    }

    @PostMapping("/product/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProduct(@PathVariable int id, @Validated @RequestBody InputProduct product) {
        productManager.update(id, product);
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable int id) {
        productManager.remove(id);
    }
}
