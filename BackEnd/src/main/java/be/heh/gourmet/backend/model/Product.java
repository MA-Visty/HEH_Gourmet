package be.heh.gourmet.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeProduct type;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
    @Column(name = "imageName")
    private String imageName;
    @Column(name = "imageId")
    private String imageId;
    @Column(name = "imageUrl")
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Ingredient> ingredients;

    public Product() {
    }

    public Product(TypeProduct type, String name, Double price, String description, String imageName, String imageId, String imageUrl, List<Ingredient> ingredients) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageName = imageName;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", type='" + getType() + "'" +
                ", name='" + getName() + "'" +
                ", price='" + getPrice() + "'" +
                ", description='" + getDescription() + "'" +
                ", imageName='" + getImageName() + "'" +
                ", imageId='" + getImageId() + "'" +
                ", imageUrl='" + getImageUrl() + "'" +
                ", ingredients='" + getIngredients() + "'" +
                "}";
    }
}
