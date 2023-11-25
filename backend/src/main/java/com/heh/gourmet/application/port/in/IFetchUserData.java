package com.heh.gourmet.application.port.in;

import com.heh.gourmet.application.domain.model.Cart;

import java.util.Optional;

public interface IFetchUserData {
    Optional<Cart> getCart();

    Optional<String> getFirstName();

    Optional<String> getLastName();

    Optional<String> getFullName();

}
