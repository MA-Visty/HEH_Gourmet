package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record InputUser(
        @Size(min = 1, max = 255, message = "last name should be between 1 and 255 characters")
        String lastname,
        @Size(min = 1, max = 255, message = "first name should be between 1 and 255 characters")
        String firstname,
        @Email(message = "email should be a valid Email")
        String email) {

    public User asUser(int id, Role role) {
        InputUser user = this;
        return new User(id, user.lastname(), user.firstname(), user.email(), role);
    }

    public static InputUser fromUser(User user) {
        return new InputUser(user.lastname(), user.firstname(), user.email());
    }
}