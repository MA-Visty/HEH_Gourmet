package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.port.in.IManageCategoryUseCase;
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
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
public class ProductController {
    @Autowired
    @Qualifier("getManageProductUseCase")
    IManageProductUseCase productManager;
    @Autowired
    @Qualifier("getManageCategoryUseCase")
    IManageCategoryUseCase categoryManager;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestBody Optional<List<Integer>> ids) {
        if (ids.isPresent()) {
            List<Product> products = productManager.batchGet(ids.get());
            if (products == null) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(products, null, HttpStatus.OK);
        }
        List<Product> products = productManager.list();
        if (products == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{category_id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable int category_id) {
        List<Product> products = productManager.listByCategory(category_id);
        if (products == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@Validated @RequestBody InputProduct product) {
        Product response = productManager.add(product);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchAddProducts(@RequestBody List<InputProduct> products) {
        productManager.batchAdd(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = productManager.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/product/{id}/category")
    public ResponseEntity<Category> getProductCategory(@PathVariable int id) {
        try {
            Category category = categoryManager.getByProduct(id);
            if (category == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(category);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
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

    @DeleteMapping("/products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchRemoveProducts(@RequestBody List<Integer> ids) {
        productManager.batchRemove(ids);
    }
}
