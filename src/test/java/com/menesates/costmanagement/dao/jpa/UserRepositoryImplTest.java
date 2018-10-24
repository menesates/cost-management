package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.UserRepository;
import com.menesates.costmanagement.exception.UserNotFoundException;
import com.menesates.costmanagement.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class UserRepositoryImplTest {

    /*
    *  import.sql dosyası ile 5 User kaydı yapıldı.
    *  3 enabled true
    *  2 enabled false
    * */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll();
        MatcherAssert.assertThat(users.size(), Matchers.equalTo(5));
    }

    @Test
    public void testFindByUserName() {
        User user = userRepository.findByUserName("user1");
        MatcherAssert.assertThat(user.getEmail(), Matchers.equalTo("a@a.com"));
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindByUserNameForUnregistererdUser() {
        User user = userRepository.findByUserName("user1Unregistered");
    }

    @Test
    public void testFindByEnabledForTrue() {
        List<User> users = userRepository.findByEnabled(true);
        MatcherAssert.assertThat(users.size(), Matchers.equalTo(3));
    }

    @Test
    public void testFindByEnabledForFalse() {
        List<User> users = userRepository.findByEnabled(false);
        MatcherAssert.assertThat(users.size(), Matchers.equalTo(2));
    }

    @Test
    public void testFindByEmail() {
        User user = userRepository.findByEmail("a@a.com");
        MatcherAssert.assertThat(user.getUsername(),Matchers.equalTo("user1"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testFindByEmailForUnregisteredUser() {
        User user = userRepository.findByEmail("testFindEmail@a.com");
    }

    @Test(expected = JpaSystemException.class)
    public void testCreateForEmptyUsername() {
        User user = new User();
        user.setUsername(null);
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setBirthDate(null);

        userRepository.create(user);

        entityManager.flush();

    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateForEmptyInvalidEmailAndPasword() {
        User user = new User();
        user.setUsername("userTest");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setBirthDate(null);

        userRepository.create(user);

        entityManager.flush();

    }

    @Test
    public void testCreateForValidParameters(){
        User user = new User();
        user.setUsername("userTest");
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("test@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        entityManager.flush();
    }

    @Test
    public void testUpdateForRegisteredUser() {
        User record = new User("userUpdate",
                "updatePass",
                true,
                "update@email.com",
                "",
                "",
                null);

        userRepository.create(record);

        User user = new User();
        user.setUsername("userUpdate");
        user.setFirstName("Update Firstname");
        user.setLastName("Update Lastname");
        user.setEmail("update@email.com");
        user.setPassword("secretTestUpdate");
        user.setBirthDate(null);

        User result = userRepository.update(user);

        MatcherAssert.assertThat(result.getUsername(), Matchers.equalTo("userUpdate"));
        MatcherAssert.assertThat(user.getEmail(), Matchers.equalTo("update@email.com"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testUpdateForRegisteredUserNullPassword() {
        User record = new User("userUpdate",
                "updatePass",
                true,
                "update@email.com",
                "",
                "",
                null);

        userRepository.create(record);

        User user = new User();
        user.setUsername("userUpdate");
        user.setEnabled(true);
        user.setFirstName("Update Firstname");
        user.setLastName("Update Lastname");
        user.setEmail("update@email.com");
        user.setPassword(null);
        user.setBirthDate(null);

        User result = userRepository.update(user);

        entityManager.flush();
    }

    @Test
    public void testUpdateForUnRegisteredUser() {
        User user = new User("userUpdate",
                "updatePass",
                true,
                "update@email.com",
                "",
                "",
                null);

        User result = userRepository.update(user);
        // todo merge hakkında öğrenmem gereken şeyler var.
        // entitymanager.merge eğer kayıt yoksa kayıt mı yapıyor.? - evet öyle.
        // ama böyle anlatılmamıştı.

        entityManager.flush();
    }

    @Test
    public void testDeleteByUsernameForRegisteredUser() {
        User record = new User("userDelete",
                "deletePass",
                true,
                "delete@email.com",
                "",
                "",
                null);

        userRepository.create(record);

        userRepository.deleteByUsername("userDelete");

        entityManager.flush();
    }

    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void testDeleteByUsernameForUnregisteredUser() {
        userRepository.deleteByUsername("userDeleteUnregister");
    }
}