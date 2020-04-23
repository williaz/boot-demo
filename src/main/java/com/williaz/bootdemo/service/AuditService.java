package com.williaz.bootdemo.service;

import com.williaz.bootdemo.domain.User;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Aspect
public class AuditService {
    public AuditService() {
        this.counter = new HashMap<>();
    }

    Map<String, Integer> counter;

    public int getCount(String key) {
        return counter.getOrDefault(key, 0);
    }

    @Pointcut("execution(* UserService.findByUsername(String)) && args(name)")
    public void getByUsername(String name){
    }

    @Before("getByUsername(name)")
    public void countGetByUsernameCalls(String name){
        String key = name + "#ByName#Total";
        counter.put(key, counter.getOrDefault(key, 0) + 1);
    }

    @AfterReturning("getByUsername(name)")
    public void countValidCalls(String name) {
        String key = name + "#ByName#Valid";
        counter.put(key, counter.getOrDefault(key, 0) + 1);
    }

    @AfterThrowing("getByUsername(name)")
    public void countInvalidCalls(String name){
        String key = name + "#ByName#Invalid";
        counter.put(key, counter.getOrDefault(key, 0) + 1);
    }

    @Pointcut("execution(* UserService.findAll())")
    public void findAllUsers(){}

    @Around("findAllUsers()")
    public void countFindAllUsersCalls(ProceedingJoinPoint jp){
        try {
            counter.put("findAll#Total", counter.getOrDefault("findAll#Total", 0) + 1);
            jp.proceed();
            counter.put("findAll#Valid", counter.getOrDefault("findAll#Valid", 0) + 1);
        } catch (Throwable e) {
            counter.put("findAll#Invalid", counter.getOrDefault("findAll#Invalid", 0) + 1);
        }
    }

    @Before("execution(* UserService.findByUsername(..)))")
    public void adviceBeforeWithJoinPoint(JoinPoint joinPoint) {
        System.out.println("Arg ######" + joinPoint.getArgs());
        System.out.println("Signa ######" + joinPoint.getSignature());
    }

}
