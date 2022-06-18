package com.in28minutes.security;

import com.in28minutes.entities.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the LoggedInUser Service.
 *
 * @author Christos Patsouras
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "/test.properties")
class LoggedInUserTest {

    /**
     * Field injection for the logged-in user.
     */
    @Autowired
    private LoggedInUser loggedInUser;

    /**
     * Test for the getLoggedInUser method using mock user.
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    void getLoggedInUser() {
        Users users = loggedInUser.getLoggedInUser();
        assertEquals("cpats", users.getUsername());
        assertTrue(users.getEnabled());
    }
}