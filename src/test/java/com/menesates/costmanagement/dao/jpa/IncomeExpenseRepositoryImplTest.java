package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.IncomeExpenseRepository;
import com.menesates.costmanagement.dao.UserRepository;
import com.menesates.costmanagement.exception.IncomeExpenseNotFoundException;
import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;
import com.menesates.costmanagement.model.enums.CostType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class IncomeExpenseRepositoryImplTest {

    @Autowired
    private IncomeExpenseRepository incomeExpenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindAll() {
        List<IncomeExpense> list = incomeExpenseRepository.findAll();
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(9));
    }

    @Test
    public void testFindByIdValid() {
        IncomeExpense ie = incomeExpenseRepository.findById(1L);
        MatcherAssert.assertThat(ie.getUser().getUsername(),Matchers.equalTo("user3"));
    }

    @Test(expected = IncomeExpenseNotFoundException.class)
    public void testFindByIdInvalid() {
        IncomeExpense ie = incomeExpenseRepository.findById(100L);
    }

    @Test
    public void testFindIncomeAll() {
        List<IncomeExpense> list = incomeExpenseRepository.findIncome();
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(5));
    }

    @Test
    public void testFindExpenseAll() {
        List<IncomeExpense> list = incomeExpenseRepository.findExpense();
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(4));
    }

    @Test
    public void testFindAllForUser() {
        User user = userRepository.findByUserName("user3");
        List<IncomeExpense> list = incomeExpenseRepository.findAll(user);
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(6));
    }

    @Test
    public void testFindIncomeForValidUser() {
        User user = userRepository.findByUserName("user3");
        List<IncomeExpense> list = incomeExpenseRepository.findIncome(user);
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(4));
    }

    @Test
    public void testFindIncomeForInvalidUser() {
        User user = new User();
        user.setUsername("userInvalidIncomeExpence");
        user.setEnabled(true);
        user.setEmail("userTestAuthority@a.com");
        user.setPassword("secretTest");
        List<IncomeExpense> list = incomeExpenseRepository.findIncome(user);
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(0));
    }

    @Test
    public void testFindExpenseForValidUser() {
        User user = userRepository.findByUserName("user3");
        List<IncomeExpense> list = incomeExpenseRepository.findExpense(user);
        MatcherAssert.assertThat(list.size(), Matchers.equalTo(2));
    }

    @Test
    public void testCreate() {
        User user = userRepository.findByUserName("user3");
        IncomeExpense ie = new IncomeExpense();
        ie.setAmount(200.0);
        ie.setCostType(CostType.Home);
        ie.setCurrencyCode(947);
        ie.setUser(user);
        ie.setDate(new Date());

        incomeExpenseRepository.create(ie);
        entityManager.flush();
    }


    @Test
    public void update() {
        User user = userRepository.findByUserName("user3");
        IncomeExpense ie = new IncomeExpense();
        ie.setAmount(200.0);
        ie.setCostType(CostType.Home);
        ie.setCurrencyCode(947);
        ie.setUser(user);
        ie.setDate(new Date());

        incomeExpenseRepository.create(ie);

        ie.setAmount(-200.0);
        IncomeExpense ie2 = incomeExpenseRepository.update(ie);
        MatcherAssert.assertThat(ie.getAmount(),Matchers.equalTo(ie2.getAmount()));
    }

    @Test
    public void delete() {
        User user = userRepository.findByUserName("user3");
        IncomeExpense ie = new IncomeExpense();
        ie.setAmount(200.0);
        ie.setCostType(CostType.Home);
        ie.setCurrencyCode(947);
        ie.setUser(user);
        ie.setDate(new Date());

        incomeExpenseRepository.create(ie);
        incomeExpenseRepository.delete(ie.getId());
        entityManager.flush();
    }
}