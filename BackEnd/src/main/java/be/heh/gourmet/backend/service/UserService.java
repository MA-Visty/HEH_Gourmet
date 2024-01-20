package be.heh.gourmet.backend.service;

import be.heh.gourmet.backend.exception.NotFoundException;
import be.heh.gourmet.backend.model.User;
import be.heh.gourmet.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        logger.debug("User create action called");
        return userRepository.save(user);
    }

    public User findOne(Long userId) {
        logger.debug("User findById action called");
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User of id " +  userId + " not found."));
    }

    public List<User> findAll() {
        logger.debug("User findAll action called");
        return userRepository.findAll();
    }

    public User update(Long userId, User user) {
        logger.debug("User update action called");
        User foundUser = findOne(userId);

        logger.debug("User found");

        foundUser.setType(user.getType());
        foundUser.setLogin(user.getLogin());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        userRepository.save(foundUser);

        logger.debug("User updated successfully");
        return foundUser;
    }

    public void delete(Long userId) {
        logger.debug("User delete action called");
        userRepository.deleteById(userId);
    }
}
