package com.in28minutes.controllers;

import com.in28minutes.entities.Todo;
import com.in28minutes.security.LoggedInUser;
import com.in28minutes.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The Todo Rest controller. Handles all the requests in "/todos" path.
 *
 * @author Christos Patsouras
 */
@RestController
@RequestMapping(value = "/todos")
public class TodoRestController {
    /**
     * The Todo Service.
     */
    private TodoService service;

    /**
     * The logged-in user.
     */
    private LoggedInUser loggedInUser;

    /**
     * Setter injection for the Todo Service.
     *
     * @param service
     *          The Todo Service.
     */
    @Autowired
    public void setService(TodoService service) {
        this.service = service;
    }

    /**
     * Setter injection for the logged-in user.
     *
     * @param loggedInUser
     *          The logged-in user.
     */
    @Autowired
    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     *
     * @return
     *          A JSON List with all the todos of the logged-in user.
     */
    @GetMapping
    public List<Todo> listAllTodos() {
        return service.retrieveTodos(loggedInUser.getLoggedInUser());
    }

    /**
     *
     * @param id
     *          The id of the Todo to be retrieved as path variable.
     * @return
     *          A JSON string with the requested Todo.
     */
    @GetMapping(value = "/{id}")
    public Todo retrieveTodo(@PathVariable("id") Integer id) {
        return service.retrieveTodo(id);
    }
}