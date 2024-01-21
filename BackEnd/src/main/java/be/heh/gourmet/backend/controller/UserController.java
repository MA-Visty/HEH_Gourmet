package be.heh.gourmet.backend.controller;

import be.heh.gourmet.backend.model.User;
import be.heh.gourmet.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> create(@RequestBody User user) {
        logger.debug("User create controller called");
        userService.create(user);
        return new ResponseEntity<>("User create successfully ! ", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        logger.debug("User findAll controller called");
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        logger.debug("User findOne controller called with id: {}", id);
        User user = userService.findOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        logger.debug("User update controller called with id: {}", id);
        User userRes = userService.update(id, user);
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.debug("User delete controller called with id: {}", id);
        userService.delete(id);
        return new ResponseEntity<>("User deleted successfully !", HttpStatus.OK);
    }

}
