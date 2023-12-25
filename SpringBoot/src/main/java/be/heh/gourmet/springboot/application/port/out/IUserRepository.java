package be.heh.gourmet.springboot.application.port.out;

import be.heh.gourmet.springboot.application.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository {

    User loadUser(String email, String password);
    List<User> loadsUser();
    void saveUser(User user);
    void updateUser(User user);
    void removeUser(int ID);
}
