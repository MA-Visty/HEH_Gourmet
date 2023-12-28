package be.heh.gourmet.adapter.in.web;

import be.heh.gourmet.application.domain.model.CartRow;
import be.heh.gourmet.application.port.in.IManageCartUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class CartController {
    @Autowired
    @Qualifier("getManageCartUseCase")
    IManageCartUseCase cartManager;

    @GetMapping("/cart")
    public ResponseEntity<List<CartRow>> getCart(String userID) {
        List<CartRow> cart = cartManager.list(userID);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cart);
    }
}
