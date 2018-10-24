package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.BudgetRepository;
import com.menesates.costmanagement.exception.BudgetNotFoundException;
import com.menesates.costmanagement.model.Budget;
import com.menesates.costmanagement.model.User;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BudgetRepositoryImpl implements BudgetRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Budget> findAll() {
        return entityManager.createNamedQuery("Budget.findAll", Budget.class).getResultList();
    }

    @Override
    public Budget findByUser(User user) {
        return entityManager.createNamedQuery("Budget.findAllForUser", Budget.class)
                .setParameter("username", user).getSingleResult();
    }

    @Override
    public Budget findById(Long id) {
        Budget budget = entityManager.find(Budget.class,id);
        if (budget == null) {
            throw new BudgetNotFoundException("Budget Not Found! id: " + id);
        }
        return budget;
    }

    @Override
    public void create(Budget budget) {
        entityManager.persist(budget);
    }

    @Override
    public Budget update(Budget budget) {
        return entityManager.merge(budget);
    }

    @Override
    public void deleteById(Long id) throws JpaObjectRetrievalFailureException {
        entityManager.remove(entityManager.getReference(Budget.class,id));
    }

    @Override
    public void deleteByUser(User user) {
        entityManager.createNamedQuery("Budget.deleteByUsername")
                .setParameter("username", user.getUsername()).executeUpdate();
    }
}
