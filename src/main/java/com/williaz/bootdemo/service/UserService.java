package com.williaz.bootdemo.service;

import com.williaz.bootdemo.domain.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByUsername(String name);
    List<User> findAll();
}
