package be.heh.gourmet.backend.application.port;

import be.heh.gourmet.backend.application.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
