package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.AuthorityRepository;
import com.menesates.costmanagement.exception.AuthorityNotFoundException;
import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorityRepositoryImpl implements AuthorityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Authority> findAll() {
        return entityManager.createNamedQuery("Authority.findAll",Authority.class).getResultList();
    }

    @Override
    public Authority findById(Long id) {
        return entityManager.find(Authority.class, id);
    }

    @Override
    public List<Authority> findUserAuthorities(User user) throws AuthorityNotFoundException{
        List<Authority> authorities = entityManager.
                createNamedQuery("Authority.findByUserAuthorities",Authority.class).
                setParameter("username",user).
                getResultList();
        if (authorities.isEmpty()){
            throw new AuthorityNotFoundException("Authority Not Found! username: " + user.getUsername());
        }
        return authorities;
    }

    @Override
    public List<Authority> findByAuthorityName(String authorityName) {
        List<Authority> authorities = entityManager.
                createNamedQuery("Authority.findByAuthorityName",Authority.class).
                setParameter("authority", authorityName).
                getResultList();
        if (authorities.isEmpty()){
            throw new AuthorityNotFoundException("Authority Not Found! authority: " + authorityName);
        }
        return authorities;
    }

    @Override
    public void create(Authority authority) {
        entityManager.persist(authority);
    }

    @Override
    public Authority update(Authority authority) {
        return entityManager.merge(authority);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(entityManager.find(Authority.class, id));
    }

    @Override
    public void deleteByUsername(String username) {
        entityManager.createNamedQuery("Authority.deleteByUsername")
                .setParameter("username", username).executeUpdate();
    }
}
