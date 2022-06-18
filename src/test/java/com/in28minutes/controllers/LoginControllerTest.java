package com.in28minutes.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

/**
 * Integration Test class for the Login Controller.
 *
 * @author Christos Patsouras
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "/test.properties")
class LoginControllerTest {

    /**
     * Field injection for the server-side Spring MVC test support.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Performs a get request to the "/" path and expect to be forwarded to the welcome page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    void showLoginPage() throws Exception {
        mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("name", "cpats"))
            .andExpect(model().hasNoErrors())
            .andExpect(forwardedUrl("/WEB-INF/views/welcome.jsp"))
            .andExpect(view().name("welcome"));
    }
}