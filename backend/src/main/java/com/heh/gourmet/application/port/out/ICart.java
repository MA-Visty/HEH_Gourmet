package com.heh.gourmet.application.port.out;

import com.heh.gourmet.application.domain.model.Cart;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * The Cart Handler
 * it allows to load, save and clear the cart
 */
public interface ICart {
    /**
     * load the cart from the storage
     *
     * @return the cart if it exists
     */
    Optional<Cart> load();

    /**
     * save the cart in the storage
     *
     * @param cart the cart to save
     */
    void save(@NotNull Cart cart);

    /**
     * clear the cart from the storage
     */
    void clear();
}
