package be.heh.gourmet.backend.repository;

import be.heh.gourmet.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
