package com.in28minutes.service;

import com.in28minutes.entities.Users;
import com.in28minutes.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Test class for User Service.
 *
 * @author Christos Patsouras
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    /**
     * Mock for the User Repository.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Mock for the User Service.
     */
    @InjectMocks
    private UserService userService;

    /**
     * A username to test.
     */
    private static String username;

    /**
     * A user to test.
     */
    private static Users users;

    /**
     * Runs before all the methods of the class and initializes the username and users static fields.
     */
    @BeforeAll
    static void initializeVariables() {
        username = "testUser";
        users = new Users(username, "", true, new ArrayList<>());
    }

    /**
     * Test for the findUserByUsername method when an existing user is given as argument to the method.
     */
    @Test
    void findUserByUsernameNormal() {
        when(userRepository.findUserByUsername(username)).thenReturn(users);
        Users returnedUsers = userService.findUserByUsername(username);
        assertEquals(users, returnedUsers);
    }

    /**
     * Test for the findUserByUsername method when a non-existing user is given as argument to the method.
     */
    @Test
    void findUserByUsernameNull() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(null);
        Users returnedUsers = userService.findUserByUsername(username);
        assertNull(returnedUsers);
    }

    /**
     * Test for the findUserByUsername method when an exception occurs in the method.
     */
    @Test
    void findUserByUsernameException() {
        doThrow(new RuntimeException()).when(userRepository).findUserByUsername(anyString());
        Assertions.assertThrows(Exception.class, () -> userService.findUserByUsername(username));
    }
}