package be.heh.gourmet.springboot.adapter.out;

import be.heh.gourmet.springboot.application.domain.model.Product;
import be.heh.gourmet.springboot.application.port.out.IProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductAdapter implements IProductRepository {

    @Override
    public List<Product> loadsProduct() {
        return null;
    }

    @Override
    public Product loadProduct(int ID) {
        return null;
    }
    @Override
    public void saveProduct(Product product) {}
    @Override
    public void updateProduct(Product product) {}
    @Override
    public void removeProduct(int ID) {}
}
