package com.heh.gourmet.adapter.ou.persistence;

import com.heh.gourmet.application.domain.model.Cart;
import com.heh.gourmet.application.port.in.IFetchUserData;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDataAdapter implements IFetchUserData {
    @Override
    public Optional<Cart> getCart() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getFirstName() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastName() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getFullName() {
        return Optional.empty();
    }
}
