package com.menesates.costmanagement.sevice;

import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.User;

import java.util.List;

public interface UserService {
    List<User> findUsers();
    User findUser(String username);
    List<User> findByEnabledUsers(Boolean enabled);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(String username);
    List<Authority> findUserAuthorities(User user);
    void addAuthorityForUser(User user, Authority authority);
    void updateAuthorityForUser(User user, Authority authority);
    void deleteAuthority(Long id);
    void deleteAllAuthorityForUser(User user);
}
