package be.heh.gourmet.application.port.out;

import be.heh.gourmet.application.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> get(int ID);
}
