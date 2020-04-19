package com.williaz.bootdemo.controller;

import com.williaz.bootdemo.domain.User;
import com.williaz.bootdemo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("all")
    Iterable<User> getAllUsers() {
        return userService.findAll();
    }
    @GetMapping("name/{username}")
    User getByUserame(@PathVariable String username) {
        return userService.findByUsername(username);
    }

}
