package com.williaz.bootdemo.service;

import com.williaz.bootdemo.domain.User;

import java.util.List;

public interface UserService {
    User findByUsername(String name);
    List<User> findAll();
}
