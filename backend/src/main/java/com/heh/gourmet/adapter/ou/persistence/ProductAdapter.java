package com.heh.gourmet.adapter.ou.persistence;

import com.heh.gourmet.application.domain.model.Product;
import com.heh.gourmet.application.port.out.IProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductAdapter implements IProductRepository {
    @Override
    public Optional<Product> load(int ID) {
        return Optional.empty();
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void remove(int ID) {

    }
}
