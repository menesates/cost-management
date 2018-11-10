package com.menesates.costmanagement.sevice;

import com.menesates.costmanagement.dao.BudgetRepository;
import com.menesates.costmanagement.dao.IncomeExpenseRepository;
import com.menesates.costmanagement.model.Budget;
import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;
import com.menesates.costmanagement.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class CostManagementServiceImpl implements CostManagementService {

    private BudgetRepository budgetRepository;
    private IncomeExpenseRepository incomeExpenseRepository;

    @Autowired
    public void setIncomeExpenseRepository(IncomeExpenseRepository incomeExpenseRepository) {
        this.incomeExpenseRepository = incomeExpenseRepository;
    }

    @Autowired
    public void setBudgetRepository(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget findBudget(User user) {
        return budgetRepository.findByUser(user);
    }

    @Override
    public List<IncomeExpense> findIncome(User user) {
        return incomeExpenseRepository.findIncome(user);
    }

    @Override
    public List<IncomeExpense> findExpense(User user) {
        return incomeExpenseRepository.findExpense(user);
    }

    @Override
    public void addCost(IncomeExpense incomeExpense) {
        incomeExpenseRepository.create(incomeExpense);
        Budget budget = budgetRepository.findByUser(incomeExpense.getUser());
        budget.setBalance(budget.getBalance() + incomeExpense.getAmount());
        if (budget.getBalance() < budget.getLowerLimit()){
            budget.setStatus(Status.Negative);
        }
        else if (budget.getBalance() > budget.getUpperLimit()){
            budget.setStatus(Status.Pozitive);
        }
        else {
            budget.setStatus(Status.Normal);
        }
        budgetRepository.update(budget);
    }

    @Override
    public void deleteCost(IncomeExpense incomeExpense) {
        incomeExpenseRepository.delete(incomeExpense.getId());
    }

    @Override
    public void updateCost(IncomeExpense incomeExpense) {
        incomeExpenseRepository.update(incomeExpense);
    }

    @Override
    public void changeBudgetUpperLimit(User user, Double upperLimit) {
        Budget budget = budgetRepository.findByUser(user);
        budget.setUpperLimit(upperLimit);
        budgetRepository.update(budget);
    }

    @Override
    public void changeBudgetLowerLimit(User user, Double lowerLimit) {
        Budget budget = budgetRepository.findByUser(user);
        budget.setUpperLimit(lowerLimit);
        budgetRepository.update(budget);
    }
}
