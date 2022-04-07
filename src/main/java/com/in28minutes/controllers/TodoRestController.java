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
import java.util.Optional;

@RestController
@RequestMapping(value = "/todos")
public class TodoRestController {

    private TodoService service;

    private LoggedInUser loggedInUser;

    @Autowired
    public void setService(TodoService service) {
        this.service = service;
    }

    @Autowired

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @GetMapping()
    public List<Todo> listAllTodos() {
        return service.retrieveTodos(loggedInUser.getLoggedInUser());
    }

    @GetMapping(value = "/{id}")
    public Optional<Todo> retrieveTodo(@PathVariable("id") int id) {
        return service.retrieveTodo(id);
    }
}