package be.heh.gourmet.adapter.in.web;


import be.heh.gourmet.adapter.in.web.exeption.CustomException;
import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import be.heh.gourmet.application.port.in.InputUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    @Qualifier("getManageUserUseCase")
    private IManageUserUseCase userManager;

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody String mail) {
        try {
            Optional<User> user = userManager.getByEmail(mail);
            if (user.isEmpty()) {
                // TODO : create user exception
                CustomException exception = new CustomException("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
                return ResponseEntity.status(exception.httpStatus()).body(exception.toResponse());
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody InputUser user) {
        try {
            User res = userManager.save(user, Role.CUSTOMER);
            return new ResponseEntity<>(res, null, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    @GetMapping("/promote/{id}")
    public ResponseEntity<Object> promote(@PathVariable int id, @RequestBody Role role) {
        try {
            throw new UnsupportedOperationException("Not implemented yet");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}
