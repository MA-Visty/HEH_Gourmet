package be.heh.gourmet.backend.controller;

import be.heh.gourmet.backend.model.User;
import be.heh.gourmet.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        logger.debug("User create controller called");
        return userService.create(user);
    }

    @GetMapping
    public List<User> findAll() {
        logger.debug("User findAll controller called");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Long id) {
        logger.debug("User findOne controller called with id: {}", id);
        return userService.findOne(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        logger.debug("User update controller called with id: {}", id);
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.debug("User delete controller called with id: {}", id);
        userService.delete(id);
    }

}
