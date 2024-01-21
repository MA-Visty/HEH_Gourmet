package be.heh.gourmet.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "type_users")
@Getter
@Setter
public class TypeUser {
    //TODO: Faire Repository ; Service ; Controller

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "type_name")
    private String typeName;

    public TypeUser() {
    }

    public TypeUser(String typeName) {
        this.typeName = typeName;
    }
}