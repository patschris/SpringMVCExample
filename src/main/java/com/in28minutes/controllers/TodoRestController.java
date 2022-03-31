package com.in28minutes.controllers;

import com.in28minutes.data.Todo;
import com.in28minutes.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.in28minutes.security.LoggedInUser.getLoggedInUserName;

@RestController
@RequestMapping(value = "/todos")
public class TodoRestController {

    private TodoService service;

    @Autowired
    public void setService(TodoService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Todo> listAllTodos() {
        return service.retrieveTodos(getLoggedInUserName());
    }

    @GetMapping(value = "/{id}")
    public Todo retrieveTodo(@PathVariable("id") int id) {
        return service.retrieveTodo(id);
    }
}
