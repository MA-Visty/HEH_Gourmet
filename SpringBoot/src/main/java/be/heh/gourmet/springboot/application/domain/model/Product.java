package be.heh.gourmet.springboot.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TABLE_PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String ingredient;
    private int category;
    private String description;

    public Product(Long id, String name, double price, String ingredient, int category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredient = ingredient;
        this.category = category;
        this.description = description;
    }

    public Product(Long id, String name, double price, String ingredient, int category) {
        this(id, name, price, ingredient, category, "");
    }

    public Product() {

    }
}
