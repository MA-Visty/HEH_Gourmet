package be.heh.gourmet.backend.repository;

import be.heh.gourmet.backend.model.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {
}
