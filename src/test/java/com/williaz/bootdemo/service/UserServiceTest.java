package com.williaz.bootdemo.service;

import com.williaz.bootdemo.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails("Will")
    public void findByUsername() {
        String name = "Will";
        assertEquals(name, userService.findByUsername(name).getUsername());
    }

    @Test
    @WithUserDetails("Will")
    public void findAll() {
        List<User> users = userService.findAll();
        assertFalse(users.isEmpty());
    }
}