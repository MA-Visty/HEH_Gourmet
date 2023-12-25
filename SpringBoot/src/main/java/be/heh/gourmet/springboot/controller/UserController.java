package be.heh.gourmet.springboot.controller;

import be.heh.gourmet.springboot.application.domain.model.User;
import be.heh.gourmet.springboot.application.port.out.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.loadsUser();
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userRepository.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }
}
