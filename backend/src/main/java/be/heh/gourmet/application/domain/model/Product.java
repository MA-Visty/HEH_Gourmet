package be.heh.gourmet.application.domain.model;

import java.net.URL;

public record Product(int ID, String name, String description, float price, int stock, URL image, int categoryID) {
}
