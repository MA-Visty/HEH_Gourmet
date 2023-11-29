package com.heh.gourmet.adapter.in.web;

import com.heh.gourmet.adapter.in.web.error.InvalidParameters;
import com.heh.gourmet.application.domain.model.Cart;
import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.domain.service.ManageCartImpl;
import com.heh.gourmet.application.port.in.IManageCartUseCase;
import com.heh.gourmet.application.port.out.ICartRepository;
import com.heh.gourmet.application.port.out.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/cart")
class CartController {
    private final ICartRepository cartRepository;
    private final IProductRepository productRepository;

    @GetMapping("")
    public ArrayList<Product> getCart() {
        // TODO : replace dummy id by the UserID
        int UserID = 1;
        Optional<Cart> cart = cartRepository.load(UserID);
        if (cart.isEmpty()) {
            throw new InvalidParameters("Cart not found for user: " + UserID);
        }
        log.debug("Load cart from storage for user: {}", UserID);
        return cart.get().getProducts();
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addItem(int id) {
        // TODO : replace dummy id by the UserID
        int UserID = 1;
        log.debug("Update Cart for user: {}\n" + "Adding product : {}", UserID, id);
        IManageCartUseCase cartHandle = new ManageCartImpl(cartRepository, productRepository);
        cartHandle.init(1);
        cartHandle.addProduct(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}