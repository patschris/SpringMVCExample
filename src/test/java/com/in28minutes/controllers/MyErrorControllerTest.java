package com.in28minutes.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

/**
 * Integration Test class for the MyError Controller.
 *
 * @author Christos Patsouras
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "/test.properties")
class MyErrorControllerTest {

    /**
     * Field injection for the server-side Spring MVC test support.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Perform a get request to "/error" path and expect to be forwarded to the error page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    void handleError() throws Exception {
        mockMvc
            .perform(get("/error"))
            .andExpect(status().isOk())
            .andExpect(model().hasNoErrors())
            .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"))
            .andExpect(view().name("error"));
    }
}