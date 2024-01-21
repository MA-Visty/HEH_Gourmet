package be.heh.gourmet.application.domain.model;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Role {
    ADMIN(100),
    EMPLOYEE(1),
    CUSTOMER(0);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public static Optional<Role> from(int value) {
        for (Role role : Role.values()) {
            if (role.getValue() == value) {
                return Optional.of(role);
            }
        }
        return Optional.empty();
    }
}
