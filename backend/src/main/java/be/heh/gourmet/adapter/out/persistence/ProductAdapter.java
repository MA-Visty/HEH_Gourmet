package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.domain.Product;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAdapter implements IManageProductUseCase {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void add(Product product) throws IllegalArgumentException {
        productRepository.add(product);
    }

    @Override
    public void update(Product product) throws IllegalArgumentException {
        productRepository.update(product);
    }

    @Override
    public void remove(int ID) throws IllegalArgumentException {
        productRepository.remove(ID);
    }

    @Override
    public Product get(int ID) throws IllegalArgumentException {
        return productRepository.get(ID);
    }

    @Override
    public List<Product> list() {
        return productRepository.getAll();
    }
}
