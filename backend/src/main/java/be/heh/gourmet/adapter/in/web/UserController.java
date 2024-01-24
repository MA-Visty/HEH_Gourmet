package be.heh.gourmet.adapter.in.web;


import be.heh.gourmet.adapter.in.web.exeption.CustomException;
import be.heh.gourmet.adapter.in.web.exeption.InternalServerError;
import be.heh.gourmet.application.domain.model.Role;
import be.heh.gourmet.application.domain.model.User;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import be.heh.gourmet.application.port.in.InputUser;
import be.heh.gourmet.application.port.in.exception.ProductException;
import be.heh.gourmet.application.port.in.exception.UserException;
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


    public record LoginRequest(String email) {
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest mail) {
        try {
            Optional<User> user = userManager.getByEmail(mail.email);
            if (user.isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            return ResponseEntity.ok(user.get());
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
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
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }

    public record PromoteRequest(Role role) {
    }

    @PostMapping("/promote/{id}")
    public ResponseEntity<Object> promote(@PathVariable int id, @RequestBody PromoteRequest role) {
        try {
            throw new UnsupportedOperationException("Not implemented yet");
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(InternalServerError.response());
        }
    }
}
