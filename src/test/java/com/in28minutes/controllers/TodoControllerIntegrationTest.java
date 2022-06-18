package com.in28minutes.controllers;

import com.in28minutes.entities.Todo;
import com.in28minutes.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * Integration Test class for the Todo Controller.
 *
 * @author Christos Patsouras
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "/test.properties")
class TodoControllerIntegrationTest {

    /**
     * Field injection for Spring MVC test support.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Field injection for the Todo Service.
     */
    @Autowired
    private TodoService todoService;

    /**
     * Performs a get request to the "/list-todos" path with a mock user.
     * Expects to be forwarded to the list-todos page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void showListTodos() throws Exception {
        mockMvc
                .perform(get("/list-todos"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", "cpats"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/list-todos.jsp"))
                .andExpect(view().name("list-todos"));
    }

    /**
     * Performs a get request to the "/list-todos" path with a mock user.
     * Expects to be forwarded to the error page because of the invalid user.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "no-one")
    public void showListTodosError() throws Exception {
        mockMvc
                .perform(get("/list-todos"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"))
                .andExpect(view().name("error"));
    }

    /**
     * Performs a get request to the "/add-todo" path with a mock user.
     * Expects to receive a todo containing only the username and to be forwarded to the todo page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void addTodo() throws Exception {
        mockMvc
                .perform(get("/add-todo"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("todo"))
                .andExpect(model().attribute("todo", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("todo", hasProperty("targetDate", is(nullValue()))))
                .andExpect(model().attribute("todo", hasProperty("description", emptyOrNullString())))
                .andExpect(model().attribute("todo", hasProperty("done", is(false))))
                .andExpect(model().attribute("todo", hasProperty("users", hasProperty("username", is("cpats")))))
                .andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/todo.jsp"))
                .andExpect(view().name("todo"));
    }

    /**
     * Performs a get request to the "/add-todo" path with a mock user.
     * Expects to receive a todo containing empty the username, because the user is invalid, and to be forwarded to the todo page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "no-one")
    public void addTodoUserNotExists() throws Exception {
        mockMvc
                .perform(get("/add-todo"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("todo"))
                .andExpect(model().attribute("todo", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("todo", hasProperty("targetDate", is(nullValue()))))
                .andExpect(model().attribute("todo", hasProperty("description", emptyOrNullString())))
                .andExpect(model().attribute("todo", hasProperty("done", is(false))))
                .andExpect(model().attribute("todo", hasProperty("users", is(nullValue()))))
                .andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/todo.jsp"))
                .andExpect(view().name("todo"));
    }

    /**
     * Performs a post request to the "/add-todo" path with a mock user.
     * Expects to be redirected to the list-todos page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void submitTodo() throws Exception {
        mockMvc
                .perform(post("/add-todo")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "Integration test")
                        .param("targetDate", "01/03/2022")
                        .sessionAttr("todo", new Todo())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:list-todos"))
                .andExpect(redirectedUrl("list-todos"));
    }

    /**
     * Performs a post request to the "/add-todo" path with a mock user, but an invalid description.
     * Expects to be forwarded to the todo page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void submitTodoError() throws Exception {
        mockMvc
                .perform(post("/add-todo")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "error")
                        .sessionAttr("todo", new Todo())
                )
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/todo.jsp"))
                .andExpect(view().name("todo"));
    }

    /**
     * Performs a get request to the "update-todo/{id}" path with a mock user.
     * Expects to receive the specified by id Todo and to be forwarded to the todo page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void updateToDoGet() throws Exception {
        Integer idToGet = todoService.getTodoMaxId();
        mockMvc
            .perform(get("/update-todo/{id}", idToGet))
            .andExpect(status().isOk())
            .andExpect(model().hasNoErrors())
            .andExpect(model().attribute("todo", hasProperty("id", is(idToGet))))
            .andExpect(forwardedUrl("/WEB-INF/views/todo.jsp"))
            .andExpect(view().name("todo"));
    }

    /**
     * Performs a get request to the "update-todo/{id}" path with a mock user.
     * Expects to receive an  empty Todo, because of the invalid id, and to be forwarded to the todo page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void updateToDoGetError() throws Exception {
        mockMvc
            .perform(get("/update-todo/{id}", 0))
            .andExpect(status().isOk())
            .andExpect(model().hasNoErrors())
            .andExpect(forwardedUrl("/WEB-INF/views/todo.jsp"))
            .andExpect(view().name("todo"));
    }

    /**
     * Performs a post request to the "update-todo/{id}" path with a mock user.
     * Expects to be redirected to the list-todos page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void updateToDoPost() throws Exception {
        Integer idToUpdate = todoService.getTodoMaxId();
        mockMvc
                .perform(post("/update-todo/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", idToUpdate.toString())
                        .param("description", "Updated from updateToDoPost")
                        .param("targetDate", "01/03/2022")
                        .sessionAttr("todo", new Todo())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/list-todos"))
                .andExpect(redirectedUrl("/list-todos"));

    }

    /**
     * Performs a post request to the "update-todo/{id}" path with a mock user.
     * Expects to be forwarded to the todo page, because of the invalid description.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void updateToDoPostError() throws Exception {
        Integer idToUpdate = todoService.getTodoMaxId();
        mockMvc
                .perform(post("/update-todo/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", idToUpdate.toString())
                        .param("description", "error")
                        .param("targetDate", "01/03/2022")
                        .sessionAttr("todo", new Todo())
                )
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/todo.jsp"))
                .andExpect(view().name("todo"));
    }

    /**
     * Performs a get request to the "delete-todo/{id}" path with a mock user.
     * Expects to be redirected to the list-todos page.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void deleteToDo() throws Exception {
        Integer idToDelete = todoService.getTodoMaxId();
        mockMvc
                .perform(get("/delete-todo/{id}", idToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/list-todos"))
                .andExpect(redirectedUrl("/list-todos"));
    }

    /**
     * Performs a get request to the "delete-todo/{id}" path with a mock user.
     * Expects to be forwarded to the error page, because of the invalid id.
     *
     * @throws Exception
     *          mockMvc.perform may throw Exception
     */
    @Test
    @WithMockUser(value = "cpats", roles = {"ADMIN", "USER"})
    public void deleteToDoError() throws Exception {
        mockMvc
                .perform(get("/delete-todo/{id}", 0))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"))
                .andExpect(view().name("error"));
    }
}