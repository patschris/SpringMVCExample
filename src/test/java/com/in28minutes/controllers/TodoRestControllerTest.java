package com.in28minutes.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import com.in28minutes.security.LoggedInUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.in28minutes.configuration.DateFormat.DATE_FORMAT;
import static com.in28minutes.configuration.DateFormat.DATE_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Test class for the Todo Rest Controller.
 *
 * @author Christos Patsouras
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "/test.properties")
class TodoRestControllerTest {

    /**
     * Field injection for the server-side Spring MVC test support.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Field injection for the logged-in user.
     */
    @Autowired
    private LoggedInUser loggedInUser;

    /**
     * An array contains four todos for testing purpose.
     */
    private final Todo[] todos = new Todo[4];

    /**
     * Runs before each method of the class and initializes the users and todos fields.
     */
    @BeforeEach
    public void before() throws ParseException {
        Users users = loggedInUser.getLoggedInUser();
        todos[0] = new Todo(1, "Buy a new car", DATE_FORMAT.parse("26/08/2022"), false, users);
        todos[1] = new Todo(2, "Find a Job", DATE_FORMAT.parse("22/12/2021"), true, users);
        todos[2] = new Todo(3, "Learn Jasper", DATE_FORMAT.parse("21/09/2022"), false, users);
        todos[3] = new Todo(4, "Learn Everything", DATE_FORMAT.parse("02/02/2222"), false, users);
    }

    /**
     * Performs a get request to the "/todos" path using a mock user.
     * Expects to receive all the todos for the specific user in a JSON array format.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    void listAllTodos() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/todos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Gson gson = new GsonBuilder().setDateFormat(DATE_PATTERN).create();
        List<Todo> returnedTodos = gson.fromJson(mvcResult.getResponse().getContentAsString(),
                                                            new TypeToken<ArrayList<Todo>>(){}.getType());
        for (Todo todo : todos) {
            assertTrue(returnedTodos.contains(todo));
        }
    }

    /**
     * Performs a get request to the "/todos/{id}" path using a mock user.
     * Expects to receive the specified by the id todo in a JSON format.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    void retrieveTodo() throws Exception {
        MvcResult mvcResult = mockMvc
            .perform(get("/todos/{id}",1).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        Gson gson =  new GsonBuilder().setDateFormat(DATE_PATTERN).create();
        Todo receivedTodo = gson.fromJson(mvcResult.getResponse().getContentAsString(),Todo.class);
        assertEquals(todos[0], receivedTodo);
    }

    /**
     * Performs a get request to the "/todos/{id}" path using a mock user.
     * Expects to receive an empty Todo as the id is invalid.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    void retrieveTodoEmpty() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/todos/{id}",0).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty());
    }
}