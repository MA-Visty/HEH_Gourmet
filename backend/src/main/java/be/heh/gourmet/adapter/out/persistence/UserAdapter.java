package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.out.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAdapter implements IUserRepository {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> get(int ID) {
        return userRepository.get(ID);
    }
}
