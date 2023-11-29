package com.heh.gourmet.adapter.ou.persistence;

import com.heh.gourmet.application.domain.model.Cart;
import com.heh.gourmet.application.port.out.ICartRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public class CartAdapter implements ICartRepository {
    @Override
    public Optional<Cart> load(int ID) {
        return Optional.empty();
    }

    @Override
    public void save(@NotNull Cart cart) {

    }

    @Override
    public void clear(int ID) {

    }
}
