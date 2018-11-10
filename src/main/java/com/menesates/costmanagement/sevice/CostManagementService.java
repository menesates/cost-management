package com.menesates.costmanagement.sevice;

import com.menesates.costmanagement.model.Budget;
import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;

import java.util.List;

public interface CostManagementService {
    Budget findBudget(User user);
    List<IncomeExpense> findIncome(User user);
    List<IncomeExpense> findExpense(User user);
    void addCost(IncomeExpense incomeExpense);
    void deleteCost(IncomeExpense incomeExpense);
    void updateCost(IncomeExpense incomeExpense);
    void changeBudgetUpperLimit(User user, Double upperLimit);
    void changeBudgetLowerLimit(User user, Double lowerLimit);
}
