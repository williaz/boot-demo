package com.williaz.bootdemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuditServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AuditService auditService;

    @Test
    @WithUserDetails("Will")
    public void countGetByUsernameCalls() {
        try {
            userService.findByUsername("Leo");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            userService.findByUsername("Leo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            userService.findByUsername("Will");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(auditService.counter);

        assertEquals(2, auditService.getCount("Leo#ByName#Total"));
//        assertEquals(2, auditService.getCount("Leo#ByName#InValid"));  // Security AOP's exception
        assertEquals(2, auditService.getCount("Leo#ByName#Valid"));

        assertEquals(1, auditService.getCount("Will#ByName#Total"));
//        assertEquals(0, auditService.getCount("Will#ByName#InValid"));
        assertEquals(1, auditService.getCount("Will#ByName#Valid"));
    }


    @Test
    @WithUserDetails("Will")
    public void countFindAllUsersCalls() {
        try {
            userService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, auditService.getCount("findAll#Total"));
        assertEquals(1, auditService.getCount("findAll#Valid"));
    }

    @Test
    @WithUserDetails("Will")
    public void adviceBeforeWithJoinPoint() {
        userService.findByUsername("Will");
    }
}