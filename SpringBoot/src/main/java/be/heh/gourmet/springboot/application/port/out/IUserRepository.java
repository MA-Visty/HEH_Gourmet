package be.heh.gourmet.springboot.application.port.out;

import be.heh.gourmet.springboot.application.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository {

    void addUser(User user);
    List<User> loadsUser();
    Optional<User> findById(Long id);
    void deleteById(Long id);
}
