package be.heh.gourmet.application.domain;

import java.net.URL;

public record Product(int ID, String name, String description, float price, int stock, URL image,
                      int categoryID) {

}
