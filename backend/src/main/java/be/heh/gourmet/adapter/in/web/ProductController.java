package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.application.port.in.exception.ProductException;
import be.heh.gourmet.application.domain.model.Category;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageCategoryUseCase;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import be.heh.gourmet.application.port.in.InputProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Object> getProducts(@RequestBody Optional<List<Integer>> ids) {
        try {
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
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/products/{category_id}")
    public ResponseEntity<Object> getProductsByCategory(@PathVariable int category_id) {
        try {
            List<Product> products = productManager.listByCategory(category_id);
            if (products == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(products);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Object> addProduct(@Validated @RequestBody InputProduct product) {
        try {
            Product response = productManager.add(product);
            if (response == null) {
                throw new ProductException("Product not created", ProductException.Type.PRODUCT_NOT_CREATED);
            }
            return ResponseEntity.created(URI.create("/api/product/" + response.ID())).body(response);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while adding product", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> batchAddProducts(@RequestBody List<InputProduct> products) {
        try {
            for (InputProduct product : products) {
                if (categoryManager.get(product.categoryID()).isEmpty()) {
                    throw new ProductException("Category does not exist", ProductException.Type.ASSOCIATED_CATEGORY_NOT_FOUND);
                }
            }
            productManager.batchAdd(products);
            // return a list of URI
            return new ResponseEntity<>(null, null, HttpStatus.CREATED);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while adding products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable int id) {
        try {
            Optional<Product> product = productManager.get(id);
            if (product.isEmpty()) {
                throw new ProductException("Product not found", ProductException.Type.PRODUCT_NOT_FOUND);
            }
            return ResponseEntity.ok(product.get());
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting product", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/product/{id}/category")
    public ResponseEntity<Object> getProductCategory(@PathVariable int id) {
        try {
            Category category = categoryManager.getByProduct(id);
            if (category == null) {
                throw new ProductException("Category does not exist", ProductException.Type.ASSOCIATED_CATEGORY_NOT_FOUND);
            }
            return ResponseEntity.ok(category);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting product category", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/product/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> updateProduct(@PathVariable int id, @Validated @RequestBody InputProduct product) {
        try {
            productManager.update(id, product);
            return ResponseEntity.created(URI.create("/api/product/" + id)).build();
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while updating product", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> removeProduct(@PathVariable int id) {
        try {
            productManager.remove(id);
            return ResponseEntity.noContent().build();
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while removing product", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @DeleteMapping("/products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> batchRemoveProducts(@RequestBody List<Integer> ids) {
        try {
            productManager.batchRemove(ids);
            return ResponseEntity.noContent().build();
        } catch (ProductException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while removing products", e);
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}
