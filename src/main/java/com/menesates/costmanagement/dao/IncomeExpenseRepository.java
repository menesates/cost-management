package com.menesates.costmanagement.dao;

import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;

import java.util.List;

public interface IncomeExpenseRepository {
    List<IncomeExpense> findAll();
    IncomeExpense findById(Long id);
    List<IncomeExpense> findIncome();
    List<IncomeExpense> findExpense();
    List<IncomeExpense> findAll(User user);
    List<IncomeExpense> findIncome(User user);
    List<IncomeExpense> findExpense(User user);
    /*
    * todo
    * tarih aralıklarına göre arama fonksiyonları eklenecek.
    * */
    void create(IncomeExpense incomeExpense);
    IncomeExpense update(IncomeExpense incomeExpense);
    void delete(Long id);
}
