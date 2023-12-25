package be.heh.gourmet.springboot.adapter.out;

import be.heh.gourmet.springboot.application.domain.model.User;
import be.heh.gourmet.springboot.application.port.out.IUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAdapter implements IUserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public User loadUser(String email, String password) {
        return null;
    }

    @Override
    public List<User> loadsUser() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void removeUser(int ID) {

    }
}
