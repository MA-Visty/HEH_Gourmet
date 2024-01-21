package be.heh.gourmet.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "type_products")
@Getter
@Setter
public class TypeProduct {
    //TODO: Faire Repository ; Service ; Controller

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "type_name")
    private String typeName;

    public TypeProduct() {
    }

    public TypeProduct(String typeName) {
        this.typeName = typeName;
    }
}