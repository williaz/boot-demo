package com.williaz.bootdemo.controller;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithUserDetails("Will")
    void getAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithUserDetails("Leo")
    void getAllUsersWithUserRoleExpect403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Leo")
    void getByUserameForSelf() throws Exception{

        String name = "Leo";
        mockMvc.perform(MockMvcRequestBuilders.get("/user/name/"+ name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(name));
    }

    @Test
    @WithUserDetails("Will")
    void getByUserameForOtherExpectPostAuthRej() throws Exception{

        String name = "Leo";
        mockMvc.perform(MockMvcRequestBuilders.get("/user/name/"+ name))
                .andExpect(status().isForbidden());
    }

    @Test
    void getByUsernameWithoutAuthExpect401() throws Exception{
        String name = "Leo";
        mockMvc.perform(MockMvcRequestBuilders.get("/user/name/"+ name))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("Arby")
    void getByUsernameWithoutAccessExpect403() throws Exception{
        String name = "Leo";
        mockMvc.perform(MockMvcRequestBuilders.get("/user/name/"+ name))
                .andExpect(status().isForbidden());
    }
}