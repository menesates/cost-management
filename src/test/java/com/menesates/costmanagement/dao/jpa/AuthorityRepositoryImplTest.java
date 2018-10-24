package com.menesates.costmanagement.dao.jpa;

import com.menesates.costmanagement.dao.AuthorityRepository;
import com.menesates.costmanagement.dao.UserRepository;
import com.menesates.costmanagement.exception.AuthorityNotFoundException;
import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
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
public class AuthorityRepositoryImplTest {

    /*
     *  import.sql dosyası ile 9 Authority kaydı yapıldı.
     *  5 ROLE_USER
     *  3 ROLE_EDITOR
     *  1 ROLE_ADMIN
     * */

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindAll() {
        List<Authority> authorities = authorityRepository.findAll();
        MatcherAssert.assertThat(authorities.size(), Matchers.equalTo(9));
    }

    @Test
    public void testFindById(){
        Authority authority = authorityRepository.findById(1L);
        MatcherAssert.assertThat(authority.getUser().getUsername(), Matchers.equalTo("user1"));
    }

    @Test
    public void testFindUserAuthoritiesForUser1() {
        User user = userRepository.findByUserName("user1");
        List<Authority> authorities = authorityRepository.findUserAuthorities(user);
        MatcherAssert.assertThat(authorities.size(),Matchers.equalTo(1));
        MatcherAssert.assertThat(authorities.get(0).getAuthority(), Matchers.equalTo("ROLE_USER"));
    }

    @Test
    public void testFindUserAuthoritiesForUser5() {
        User user = userRepository.findByUserName("user5");
        List<Authority> authorities = authorityRepository.findUserAuthorities(user);
        MatcherAssert.assertThat(authorities.size(),Matchers.equalTo(3));
        MatcherAssert.assertThat(authorities.get(0).getAuthority(), Matchers.equalTo("ROLE_USER"));
        MatcherAssert.assertThat(authorities.get(1).getAuthority(), Matchers.equalTo("ROLE_EDITOR"));
        MatcherAssert.assertThat(authorities.get(2).getAuthority(), Matchers.equalTo("ROLE_ADMIN"));
    }

    @Test(expected = AuthorityNotFoundException.class)
    public void testFindUserAuthoritiesForInvalidUser(){
        User user = new User();
        user.setUsername("TestUserName");
        List<Authority> authorities = authorityRepository.findUserAuthorities(user);
    }

    @Test
    public void testFindByAuthorityNameForRoleUser(){
        List<Authority> authorities = authorityRepository.findByAuthorityName("ROLE_USER");
        MatcherAssert.assertThat(authorities.size(),Matchers.equalTo(5));
    }

    @Test
    public void testFindByAuthorityNameForRoleAdmin(){
        List<Authority> authorities = authorityRepository.findByAuthorityName("ROLE_ADMIN");
        MatcherAssert.assertThat(authorities.size(),Matchers.equalTo(1));
    }

    @Test(expected = AuthorityNotFoundException.class)
    public void testFindByAuthorityNameForInvalidRole(){
        List<Authority> authorities = authorityRepository.findByAuthorityName("ROLE_INVALID");
    }

    @Test
    public void testCreateValidParameter() {
        User user = new User();
        user.setUsername("userTestAuthority");
        user.setEnabled(true);
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("userTestAuthority@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Authority record = new Authority(user,"ROLE_USER");
        authorityRepository.create(record);
        entityManager.flush();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateForEmptyParameter() {
        Authority record = new Authority(null,null);
        authorityRepository.create(record);

        entityManager.flush();
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testCreateForInvalidUser() {
        User user = new User();
        user.setUsername("userInvalid");
        user.setEnabled(true);
        user.setEmail("userTestAuthority@a.com");
        user.setPassword("secretTest");

        Authority record = new Authority(user,"ROLE_USER");
        authorityRepository.create(record);

        entityManager.flush();
    }

    @Test
    public void update() {
        User user = userRepository.findByUserName("user1");
        List<Authority> authorities = authorityRepository.findUserAuthorities(user);

        authorities.get(0).setAuthority("ROLE_TEST");
        authorityRepository.update(authorities.get(0));
        entityManager.flush();
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setUsername("userTestDeleteAuthority");
        user.setEnabled(true);
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("userTestAuthority@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Authority authority = new Authority(user,"ROLE_DELETE");
        authorityRepository.create(authority);

        authorityRepository.deleteById(authority.getId());
        userRepository.deleteByUsername(user.getUsername());
        entityManager.flush();
    }

    @Test(expected = AuthorityNotFoundException.class)
    public void deleteByUsername() {
        User user = new User();
        user.setUsername("userTestDeleteByUsernameAuthority");
        user.setEnabled(true);
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail("userTestAuthority@a.com");
        user.setPassword("secretTest");
        user.setBirthDate(null);

        userRepository.create(user);

        Authority authority1 = new Authority(user,"ROLE_DELETE1");
        Authority authority2 = new Authority(user,"ROLE_DELETE2");
        authorityRepository.create(authority1);
        authorityRepository.create(authority2);

        authorityRepository.deleteByUsername(user.getUsername());
        authorityRepository.findUserAuthorities(user);
    }
}