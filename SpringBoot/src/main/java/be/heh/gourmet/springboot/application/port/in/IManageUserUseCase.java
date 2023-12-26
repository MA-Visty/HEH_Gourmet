package be.heh.gourmet.springboot.application.port.in;

import be.heh.gourmet.springboot.application.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IManageUserUseCase {

    void createUser(User user);
    void removeUser(Long ID);
}
