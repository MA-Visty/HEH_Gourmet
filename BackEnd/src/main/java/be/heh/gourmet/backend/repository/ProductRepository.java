package be.heh.gourmet.backend.repository;

import be.heh.gourmet.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
