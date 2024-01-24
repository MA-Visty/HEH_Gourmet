package be.heh.gourmet.application.domain.model;

public record User(int id, String lastname, String firstname, String email, Role role) {
}