package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.port.in.IManageFavUseCase;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import be.heh.gourmet.application.port.in.IManageUserUseCase;
import be.heh.gourmet.application.port.in.exception.FavException;
import be.heh.gourmet.application.port.in.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/{userID}/fav")
public class FavController {
    @Autowired
    @Qualifier("getManageFavUseCase")
    IManageFavUseCase favManager;

    @Autowired
    @Qualifier("getUserUseCase")
    IManageUserUseCase userManager;

    @Autowired
    @Qualifier("getManageProductUseCase")
    IManageProductUseCase productManager;

    @GetMapping("")
    public ResponseEntity<Object> getFav(@PathVariable int userID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            List<Integer> fav = favManager.list(userID);
            return ResponseEntity.ok().body(productManager.batchGet(fav));
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (FavException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while getting fav", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{productID}")
    public ResponseEntity<Object> addFav(@PathVariable int userID, @PathVariable int productID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            favManager.add(userID, productID);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (FavException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while adding fav", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{productID}")
    public ResponseEntity<Object> removeFav(@PathVariable int userID, @PathVariable int productID) {
        try {
            if (userManager.get(userID).isEmpty()) {
                throw new UserException("User not found", UserException.Type.USER_NOT_FOUND);
            }
            favManager.remove(userID, productID);
            return ResponseEntity.ok().build();
        } catch (UserException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (FavException e) {
            return new ResponseEntity<>(e.toResponse(), null, e.httpStatus());
        } catch (Exception e) {
            log.error("Error while removing fav", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
