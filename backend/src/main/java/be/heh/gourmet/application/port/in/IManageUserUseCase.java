package be.heh.gourmet.application.port.in;

import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;

import java.util.Optional;

public interface IManageUserUseCase {
    Optional<User> get(int ID);

    Optional<User> getByEmail(String email);

    User save(InputUser user, Role role);
}
