package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.IncomeExpenseRepository;
import com.menesates.costmanagement.exception.IncomeExpenseNotFoundException;
import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class IncomeExpenseRepositoryImpl implements IncomeExpenseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IncomeExpense> findAll() {
        return entityManager.createNamedQuery("IncomeExpense.findAll", IncomeExpense.class)
                .getResultList();
    }

    @Override
    public IncomeExpense findById(Long id) throws IncomeExpenseNotFoundException {
        IncomeExpense ie = entityManager.find(IncomeExpense.class,id);
        if (ie == null){
            throw new IncomeExpenseNotFoundException("Income-Expence Not found! id:" + id);
        }
        return ie;
    }

    @Override
    public List<IncomeExpense> findIncome() {
        return entityManager.createNamedQuery("IncomeExpense.findIncome").getResultList();
    }

    @Override
    public List<IncomeExpense> findExpense() {
        return entityManager.createNamedQuery("IncomeExpense.findExpence").getResultList();
    }

    @Override
    public List<IncomeExpense> findAll(User user) {
        return entityManager.createNamedQuery("IncomeExpense.findAllForUser")
                .setParameter("username",user).getResultList();
    }

    @Override
    public List<IncomeExpense> findIncome(User user) {
        return entityManager.createNamedQuery("IncomeExpense.findIncomeForUser")
                .setParameter("username",user).getResultList();
    }

    @Override
    public List<IncomeExpense> findExpense(User user) {
        return entityManager.createNamedQuery("IncomeExpense.findExpenceForUser")
                .setParameter("username",user).getResultList();
    }

    @Override
    public void create(IncomeExpense incomeExpense) {
        entityManager.persist(incomeExpense);
    }

    @Override
    public IncomeExpense update(IncomeExpense incomeExpense) {
        return entityManager.merge(incomeExpense);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(IncomeExpense.class,id));
    }
}
