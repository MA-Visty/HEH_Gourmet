package com.heh.gourmet.application.port.out;

import com.heh.gourmet.application.domain.model.Cart;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The Cart Handler
 * it allows to load, save and clear the cart
 */
@Repository
public interface ICartRepository {
    /**
     * load the cart from the storage
     *
     * @return the cart if it exists
     */
    Optional<Cart> load(int ID);

    /**
     * save the cart in the storage
     *
     * @param cart the cart to save
     */
    void save(@NotNull Cart cart);

    /**
     * clear the cart from the storage
     */
    void clear(int ID);
}
