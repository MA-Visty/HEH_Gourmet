package be.heh.gourmet.springboot.application.port.in;

import be.heh.gourmet.springboot.application.domain.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface IManageProductUseCase {
    void createProduct(Product product);
    void updateProduct(Product product);
    void removeProduct(int ID);
}
