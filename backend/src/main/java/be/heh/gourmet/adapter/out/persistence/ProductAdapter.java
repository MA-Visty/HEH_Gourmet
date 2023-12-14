package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.port.in.InputProduct;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAdapter implements IManageProductUseCase {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product add(InputProduct product) throws IllegalArgumentException {
        int ID = productRepository.add(product);
        return product.asProduct(ID, product.image());
    }

    @Override
    public void batchAdd(List<InputProduct> products) throws IllegalArgumentException {
        productRepository.batchAdd(products);
    }

    @Override
    public void update(int ID, InputProduct product) throws IllegalArgumentException {
        productRepository.update(ID, product);
    }

    @Override
    public void remove(int ID) throws IllegalArgumentException {
        productRepository.remove(ID);
    }

    @Override
    public void batchRemove(List<Integer> IDs) throws IllegalArgumentException {
        productRepository.batchRemove(IDs);
    }

    @Override
    public Product get(int ID) throws IllegalArgumentException {
        return productRepository.get(ID);
    }

    @Override
    public List<Product> batchGet(List<Integer> IDs) throws IllegalArgumentException {
        return productRepository.batchGet(IDs);
    }

    @Override
    public List<Product> list() {
        return productRepository.getAll();
    }

    @Override
    public List<Product> listByCategory(int categoryID) {
        return productRepository.getFromCategory(categoryID);
    }
}
