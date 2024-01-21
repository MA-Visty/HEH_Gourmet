package be.heh.gourmet.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "products")
    private List<Product> products;
    @Column(name = "price")
    private Double price;
    @Column(name = "state")
    private String state;
    @Column(name = "date")
    private String date;

    public Order() {
    }

    public Order(User userId, List<Product> products, Double price, String state, String date) {
        this.userId = userId;
        this.products = products;
        this.price = price;
        this.state = state;
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", userId='" + getUserId() + "'" +
                ", products='" + getProducts() + "'" +
                ", price='" + getPrice() + "'" +
                ", state='" + getState() + "'" +
                ", date='" + getDate() + "'" +
                "}";
    }
}
