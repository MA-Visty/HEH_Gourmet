package be.heh.gourmet.backend.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeUser type;
    @Column(name = "login")
    private String login;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    public User() {
    }

    public User(TypeUser type, String login, String email, String password) {
        this.type = type;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", type='" + getType() + "'" +
                ", login='" + getLogin() + "'" +
                ", email='" + getEmail() + "'" +
                "}";
    }
}
