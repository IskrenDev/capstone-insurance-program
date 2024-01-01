package com.github.iskrendev.insuranceprogram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iskrendev.insuranceprogram.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    void getMe_whenLoggedIn_expectStatus200() throws Exception {
        AppUser expectedAppUser = new AppUser(1, "test");
        String expectedJson = objectMapper.writeValueAsString(expectedAppUser);
        mockMvc.perform(get("/api/auth/me")
                        .with(oidcLogin().userInfoToken(token -> {
                            token.claims(claim -> {
                                claim.put("id", "1");
                                claim.put("login", "test");
                            });
                        }))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    @DirtiesContext
    void getMe_whenNotLoggedIn_expectStatus401() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }
}