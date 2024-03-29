package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import be.heh.gourmet.application.port.in.InputUser;
import be.heh.gourmet.application.port.in.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAdapter implements IManageUserUseCase {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> get(int ID) {
        return userRepository.get(ID);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public User save(InputUser user, Role role) {
        if (userRepository.getByEmail(user.email()).isPresent()) {
            throw new UserException("User already exists", UserException.Type.USER_ALREADY_EXIST);
        }
        int id = userRepository.add(user, role);
        return user.asUser(id, role);
    }
}
