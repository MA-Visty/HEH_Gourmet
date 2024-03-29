package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

@NonNull
public record InputProduct(
        @Size(min = 1, max = 255, message = "name should be between 1 and 255 characters")
        String name,
        @Size(min = 1, max = 2048, message = "description should be between 1 and 2048 characters")
        String description,
        @DecimalMin(value = "0.1", message = "price should not be less than 0")
        float price,
        @Min(value = 0, message = "stock should not be less than 0")
        int stock,
        java.net.URL image,
        @Min(value = 0, message = "categoryID should not be less than 0")
        int categoryID) {

    public Product asProduct(int ID, java.net.URL image) {
        InputProduct product = this;
        return new Product(ID, product.name(), product.description(),
                product.price(), product.stock(), image, product.categoryID());
    }

    public InputProduct fromProduct(Product product) {
        return new InputProduct(product.name(), product.description(),
                product.price(), product.stock(), product.image(), product.categoryID());
    }
}

