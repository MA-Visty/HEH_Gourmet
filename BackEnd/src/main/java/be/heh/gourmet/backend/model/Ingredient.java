package be.heh.gourmet.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;

    public Ingredient() {
    }

    public Ingredient(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}