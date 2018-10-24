package com.menesates.costmanagement.dao;

import com.menesates.costmanagement.model.Budget;
import com.menesates.costmanagement.model.User;

import java.util.List;

public interface BudgetRepository {
    List<Budget> findAll();
    Budget findByUser(User user);
    Budget findById(Long id);
    void create(Budget budget);
    Budget update(Budget budget);
    void deleteById(Long id);
    void deleteByUser(User user);
}
