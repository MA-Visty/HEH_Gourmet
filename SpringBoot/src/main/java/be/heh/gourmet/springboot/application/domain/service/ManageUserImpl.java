package be.heh.gourmet.springboot.application.domain.service;

import be.heh.gourmet.springboot.application.domain.model.User;
import be.heh.gourmet.springboot.application.port.in.IManageUserUseCase;
import be.heh.gourmet.springboot.application.port.out.IUserRepository;

public class ManageUserImpl implements IManageUserUseCase {

    private final IUserRepository userRepository;

    public ManageUserImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.saveUser(user);
    }
    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
    @Override
    public void removeUser(int ID) {
        userRepository.removeUser(ID);
    }
}