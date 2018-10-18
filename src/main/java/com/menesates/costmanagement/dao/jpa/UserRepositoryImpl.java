package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.UserRepository;
import com.menesates.costmanagement.exception.UserNotFoundException;
import com.menesates.costmanagement.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }

    @Override
    public User findByUserName(String username) throws UserNotFoundException {
        User user = entityManager.find(User.class,username);
        if (user == null){
            throw new UserNotFoundException("User not found! username: " + username);
        }
        return entityManager.find(User.class, username);
    }

    @Override
    public List<User> findByEnabled(Boolean enabled) {
        return entityManager.createNamedQuery("User.findByEnabled",User.class)
                .setParameter("enabled", enabled).getResultList();
    }

    @Override
    public User findByEmail(String email) throws EmptyResultDataAccessException {
        return entityManager.createNamedQuery("User.findByEmail",User.class)
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public void deleteByUsername(String username) throws JpaObjectRetrievalFailureException {
        entityManager.remove(entityManager.getReference(User.class,username));
    }
}
