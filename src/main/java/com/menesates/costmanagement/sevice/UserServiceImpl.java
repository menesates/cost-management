package com.menesates.costmanagement.sevice;

import com.menesates.costmanagement.dao.AuthorityRepository;
import com.menesates.costmanagement.dao.UserRepository;
import com.menesates.costmanagement.exception.AuthoriyDoesNotMatchUserException;
import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<User> findByEnabledUsers(Boolean enabled) {
        return null;
    }

    @Override
    public void createUser(User user) {
        userRepository.create(user);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);
        authorityRepository.create(authority);
    }

    @Override
    public void updateUser(User user) {
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public List<Authority> findUserAuthorities(User user) {
        return authorityRepository.findUserAuthorities(user);
    }

    @Override
    public void addAuthorityForUser(User user, Authority authority) {
        User selectUser = userRepository.findByUserName(user.getUsername());
        if (selectUser.getUsername().equals(authority.getUser().getUsername())){
            authorityRepository.create(authority);
        } else {
            throw new AuthoriyDoesNotMatchUserException("Authority username: " + authority.getUser().getUsername() +
                    " user username: " + user.getUsername() + "does not match.!");
        }
    }

    @Override
    public void updateAuthorityForUser(User user, Authority authority) {
        User selectUser = userRepository.findByUserName(user.getUsername());
        if (selectUser.getUsername().equals(authority.getUser().getUsername())){
            authorityRepository.update(authority);
        } else {
            throw new AuthoriyDoesNotMatchUserException("Authority username: " + authority.getUser().getUsername() +
                    " user username: " + user.getUsername() + "does not match.!");
        }
    }

    @Override
    public void deleteAuthority(Long id) {
        authorityRepository.deleteById(id);
    }

    @Override
    public void deleteAllAuthorityForUser(User user) {
        authorityRepository.deleteByUsername(user.getUsername());
    }

}
