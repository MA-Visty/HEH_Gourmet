package be.heh.gourmet.springboot.application.port.out;

import be.heh.gourmet.springboot.application.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;

@Repository
public interface IProductRepository {
    Product loadProduct(int ID);
    List<Product> loadsProduct();
    void saveProduct(Product product);
    void updateProduct(Product product);
    void removeProduct(int ID);
}
