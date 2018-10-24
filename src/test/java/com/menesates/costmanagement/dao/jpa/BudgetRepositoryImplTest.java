package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.BudgetRepository;
import com.menesates.costmanagement.dao.UserRepository;
import com.menesates.costmanagement.exception.BudgetNotFoundException;
import com.menesates.costmanagement.model.Budget;
import com.menesates.costmanagement.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class BudgetRepositoryImplTest {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindAll() {
        List<Budget> budgets = budgetRepository.findAll();
        MatcherAssert.assertThat(budgets.size(), Matchers.equalTo(5));
    }

    @Test
    public void testFindByUser() {
        User user = userRepository.findByUserName("user1");
        Budget budget = budgetRepository.findByUser(user);
        MatcherAssert.assertThat(budget.getUser(), Matchers.equalTo(user));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testFindByUserInvalid() {
        User user = new User();
        user.setUsername("userTest");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);
        Budget budget = budgetRepository.findByUser(user);
        MatcherAssert.assertThat(budget.getUser(), Matchers.equalTo(user));
    }

    @Test
    public void testFindById() {
        Budget budget = budgetRepository.findById(1L);
        MatcherAssert.assertThat(budget.getUser().getUsername(), Matchers.equalTo("user1"));
    }

    @Test(expected = BudgetNotFoundException.class)
    public void testFindByIdInvalid() {
        Budget budget = budgetRepository.findById(100L);
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setUsername("userTest");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setBalance(200D);
        budgetRepository.create(budget);

    }

    @Test
    public void testCreateInvalid() {
        User user = new User();
        user.setUsername("userTest2");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setBalance(200D);
        budgetRepository.create(budget);

        Budget newBudget = new Budget();
        budget.setUser(user);
        budget.setBalance(1000D);
        budgetRepository.create(budget);
        entityManager.flush();
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setUsername("userTest2");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setBalance(200D);
        budgetRepository.create(budget);

        budget.setBalance(10000D);
        budgetRepository.update(budget);
        entityManager.flush();
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setUsername("userTest2");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setBalance(200D);
        budgetRepository.create(budget);

        budgetRepository.deleteById(budget.getId());
        entityManager.flush();
    }

    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void deleteByIdInvalid() {
        budgetRepository.deleteById(1000L);
    }

    @Test
    public void deleteByUser() {
        User user = new User();
        user.setUsername("userTestDeleteByUser");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setBalance(200D);
        budgetRepository.create(budget);

        budgetRepository.deleteByUser(user);
        entityManager.flush();

    }
}