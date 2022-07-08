package com.in28minutes.controllers;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import com.in28minutes.service.LoggedInUserService;
import com.in28minutes.service.TodoService;
import org.apache.log4j.Logger;
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
     * The logged-in user service.
     */
    private LoggedInUserService loggedInUserService;

    /**
     * The debug logger.
     */
    private final Logger log = Logger.getLogger("debugLog");

    /**
     * The class name.
     */
    private final String className = getClass().getName();

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
     * Setter injection for the logged-in user service.
     *
     * @param loggedInUserService
     *          The logged-in user service.
     */
    @Autowired
    public void setLoggedInUser(LoggedInUserService loggedInUserService) {
        this.loggedInUserService = loggedInUserService;
    }

    /**
     *
     * @return
     *          A JSON List with all the todos of the logged-in user.
     */
    @GetMapping
    public List<Todo> listAllTodos() {
        Users user = loggedInUserService.getLoggedInUser();
        List<Todo> todos = service.retrieveTodos(user);
        log.info(className + "." + new Object(){}.getClass().getEnclosingMethod().getName() +
                " - Get request at /todos for " + user.getUsername() + " returned " + todos);
        return todos;
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
        Todo todo = service.retrieveTodo(id);
        log.info(className + "." + new Object(){}.getClass().getEnclosingMethod().getName() +
                " - request at /todos/" + id + " for " + loggedInUserService.getLoggedInUser().getUsername() +
                " returned " + todo);
        return todo;
    }
}