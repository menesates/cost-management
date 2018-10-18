package com.menesates.costmanagement.dao;

import com.menesates.costmanagement.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findByUserName(String username);
    List<User> findByEnabled(Boolean enabled);
    User findByEmail(String email);
    void create(User user);
    User update(User user);
    void deleteByUsername(String username);
}
