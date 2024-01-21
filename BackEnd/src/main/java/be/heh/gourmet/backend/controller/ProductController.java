package be.heh.gourmet.backend.controller;

import be.heh.gourmet.backend.model.Product;
import be.heh.gourmet.backend.service.CloudinaryService;
import be.heh.gourmet.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
    }

    //TODO: Faire si l'image est trop grande ou si différent de jpg
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(@RequestPart("product") Product product,
                                         @RequestPart("file") MultipartFile multipartFile) throws IOException {
        logger.debug("Product create controller called");
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image not available!", HttpStatus.BAD_REQUEST);
        }
        logger.debug("Cloudinary upload service called");
        Map result = cloudinaryService.upload(multipartFile);
        product.setImageName((String) result.get("original_filename"));
        product.setImageId((String) result.get("public_id"));
        product.setImageUrl((String) result.get("url"));

        logger.debug("Product create service called");
        productService.create(product);
        return new ResponseEntity<>("Product create successfully ! ", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        logger.debug("Product findAll controller called");
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id) {
        logger.debug("Product findOne controller called with id: {}", id);
        Product product = productService.findOne(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> update(@PathVariable Long id,
                                          @RequestPart("product") Product product,
                                          @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        logger.debug("Product update controller called");
        if (multipartFile != null && !multipartFile.isEmpty()) {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                return new ResponseEntity<>("Image not available!", HttpStatus.BAD_REQUEST);
            }
            logger.debug("Cloudinary upload service called for update");
            Map result = cloudinaryService.upload(multipartFile);
            product.setImageName((String) result.get("original_filename"));
            product.setImageId((String) result.get("public_id"));
            product.setImageUrl((String) result.get("url"));
        }

        logger.debug("Product update controller called with id: {}", id);
        productService.update(id, product);
        return new ResponseEntity<>("Product updated successfully !", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.debug("Product delete controller called with id: {}", id);
        productService.delete(id);
        return new ResponseEntity<>("Product deleted successfully !", HttpStatus.OK);
    }
}
