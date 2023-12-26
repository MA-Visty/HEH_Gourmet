package be.heh.gourmet.springboot.adapter.out;

import be.heh.gourmet.springboot.application.domain.model.User;
import be.heh.gourmet.springboot.application.port.out.IUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAdapter implements IUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> loadsUser() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
    }
}
