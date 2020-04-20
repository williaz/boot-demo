package com.williaz.bootdemo.config;

import com.williaz.bootdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService)
        .passwordEncoder(NoOpPasswordEncoder.getInstance()); // require an encoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .rememberMe()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers("/h2-console")
                .hasRole("ADMIN")
                .mvcMatchers("/user/**") // sub path
                .hasAnyRole("USER", "ADMIN")
                .mvcMatchers("/", "/**").access("permitAll");
    }
}
