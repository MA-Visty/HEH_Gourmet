package be.heh.gourmet.adapter.out.persistence;

import be.heh.gourmet.application.port.in.exception.ProductException;
import be.heh.gourmet.application.port.in.InputProduct;
import be.heh.gourmet.application.domain.model.Product;
import be.heh.gourmet.application.port.in.IManageProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductAdapter implements IManageProductUseCase {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product add(InputProduct product) throws ProductException {
        // check if category exists
        if (categoryRepository.get(product.categoryID()).isEmpty()) {
            throw new ProductException("Category does not exist", ProductException.Type.ASSOCIATED_CATEGORY_NOT_FOUND);
        }
        int ID = productRepository.add(product);
        return product.asProduct(ID, product.image());
    }

    @Override
    public void batchAdd(List<InputProduct> products) throws ProductException {
        productRepository.batchAdd(products);
    }

    @Override
    public void update(int ID, InputProduct product) throws ProductException {
        productRepository.update(ID, product);
    }

    @Override
    public void remove(int ID) throws ProductException {
        productRepository.remove(ID);
    }

    @Override
    public void batchRemove(List<Integer> IDs) throws ProductException {
        productRepository.batchRemove(IDs);
    }

    @Override
    public Optional<Product> get(int ID) throws ProductException {
        return productRepository.get(ID);
    }

    @Override
    public List<Product> batchGet(List<Integer> IDs) throws ProductException {
        return productRepository.batchGet(IDs);
    }

    @Override
    public List<Product> list() {
        return productRepository.getAll();
    }

    @Override
    public List<Product> listByCategory(int categoryID) {
        if (categoryRepository.get(categoryID).isEmpty()) {
            throw new ProductException("Category does not exist", ProductException.Type.ASSOCIATED_CATEGORY_NOT_FOUND);
        }
        return productRepository.getFromCategory(categoryID);
    }
}
