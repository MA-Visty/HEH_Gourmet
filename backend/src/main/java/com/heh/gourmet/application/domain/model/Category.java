package com.heh.gourmet.application.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@NotNull
public class Category {
    @Id
    private final int ID;
    @Setter
    private String name;
    @Setter
    private String description;

    public Category(int ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
    }

    public Category(int ID, String name) {
        this(ID, name, "");
    }
}
