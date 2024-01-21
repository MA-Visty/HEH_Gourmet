package be.heh.gourmet.backend.controller;

import be.heh.gourmet.backend.model.Order;
import be.heh.gourmet.backend.model.TypeProduct;
import be.heh.gourmet.backend.service.OrderService;
import be.heh.gourmet.backend.service.TypeProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class TypeProductController {

    private static final Logger logger = LoggerFactory.getLogger(TypeProductController.class);

    private final TypeProductService typeProductService;

    @Autowired
    public TypeProductController(TypeProductService typeProductService) {
        this.typeProductService = typeProductService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody TypeProduct typeProduct) {
        logger.debug("TypeProduct create controller called");
        typeProductService.create(typeProduct);
        return new ResponseEntity<>("TypeProduct create successfully ! ", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TypeProduct>> findAll() {
        logger.debug("TypeProduct findAll controller called");
        List<TypeProduct> typesProduct = typeProductService.findAll();
        return new ResponseEntity<>(typesProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeProduct> findOne(@PathVariable Long id) {
        logger.debug("TypeProduct findOne controller called with id: {}", id);
        TypeProduct typeProduct = typeProductService.findOne(id);
        return new ResponseEntity<>(typeProduct, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeProduct> update(@PathVariable Long id, @RequestBody TypeProduct typeProduct) {
        logger.debug("TypeProduct update controller called with id: {}", id);
        TypeProduct typeProductRes = typeProductService.update(id, typeProduct);
        return new ResponseEntity<>(typeProductRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.debug("TypeProduct delete controller called with id: {}", id);
        typeProductService.delete(id);
        return new ResponseEntity<>("TypeProduct deleted successfully !", HttpStatus.OK);
    }
}
