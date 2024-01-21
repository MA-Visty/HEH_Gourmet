package be.heh.gourmet.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    //TODO: A faire

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    //private final UserService userService;

    /*
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
     */

    //@PostMapping
    public ResponseEntity<String> login() {
        logger.debug("Auth login controller called");
        // TODO: Login methode
        return new ResponseEntity<>("Auth login successfully ! ", HttpStatus.OK);
    }

    //@GetMapping
    public ResponseEntity<String> logout() {
        logger.debug("Auth logout controller called");
        // TODO: Logout methode
        return new ResponseEntity<>("Auth logout successfully ! ", HttpStatus.OK);
    }
}