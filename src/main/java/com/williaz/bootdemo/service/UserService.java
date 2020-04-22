package com.williaz.bootdemo.service;

import com.williaz.bootdemo.domain.User;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    @Secured({"ROLE_ADMIN", "Role_USER"})
//    @PreAuthorize("hasRole('Role_USER') or hasRole('ROLE_ADMIN')") // TODO unexpected
    @PostAuthorize("returnObject.username == principal.username") // only show self
    User findByUsername(String name);

    @Secured({"ROLE_ADMIN", "ROLE_AUDIT"})
    List<User> findAll();
}
