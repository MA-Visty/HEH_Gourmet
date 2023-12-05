package be.heh.gourmet.application.domain;

import java.net.URL;

public record InputProduct(String name, String description, float price, int stock, URL image,
                           int categoryID) {

}

