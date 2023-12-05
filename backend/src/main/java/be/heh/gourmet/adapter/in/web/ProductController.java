package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.domain.InputProduct;
import be.heh.gourmet.application.domain.Product;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class ProductController {
    @Autowired
    @Qualifier("getManageProductUseCase")
    IManageProductUseCase productManager;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productManager.list();
        if (products == null) {
            return new ResponseEntity<>(null, null, 404);
        }
        return new ResponseEntity<>(products, null, 200);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = productManager.get(id);
        if (product == null) {
            return new ResponseEntity<>(null, null, 404);
        }
        return new ResponseEntity<>(product, null, 200);
    }

    @PostMapping ("/product")
    public ResponseEntity<Product> addProduct(@RequestBody InputProduct product) {
        try {
            Product response = productManager.add(product);
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<>(response, responseHeaders, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(null, null, 400);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> removeProduct(@PathVariable int id) {
        try {
            productManager.remove(id);
            return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, null, 400);
        }
    }
}
