package be.heh.gourmet.application.domain;

import java.net.URL;

public record Product(int ID, String name, String description, float price, int stock, URL image,
                      int categoryID) {
    public static Product of(int ID, URL image, InputProduct product) {
        return new Product(ID, product.name(), product.description(),
                product.price(), product.stock(), image, product.categoryID());
    }
}
