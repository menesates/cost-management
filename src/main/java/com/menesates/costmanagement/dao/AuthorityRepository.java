package com.menesates.costmanagement.dao;

import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.User;

import java.util.List;

public interface AuthorityRepository {
    List<Authority> findAll();
    Authority findById(Long id);
    List<Authority> findUserAuthorities(User user); // user nesnesi mi yoksa username mi almalı
    List<Authority> findByAuthorityName(String authorityName);
    void create(Authority authority);
    Authority update(Authority authority);
    void deleteById(Long id);
    void deleteByUsername(String username); // user nesnesi mi yoksa username mi almalı
}
