package com.williaz.bootdemo.controller;

import com.williaz.bootdemo.domain.User;
import com.williaz.bootdemo.service.UserService;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Secured({"ROLE_ADMIN", "ROLE_AUDIT"})
    @GetMapping("all")
    Iterable<User> getAllUsers() {
        return userService.findAll();
    }

    @Secured({"ROLE_ADMIN", "Role_USER"})
//    @PreAuthorize("hasRole('Role_USER') or hasRole('ROLE_ADMIN')") // TODO unexpected
    @PostAuthorize("returnObject.username == principal.username") // only show self
    @GetMapping("name/{username}")
    User getByUserame(@PathVariable String username) {
        return userService.findByUsername(username);
    }

}
