package com.in28minutes.controllers;

import com.in28minutes.entities.Todo;
import com.in28minutes.security.LoggedInUser;
import com.in28minutes.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TodoController {

    private TodoService todoService;
    private LoggedInUser loggedInUser;

    @Autowired
    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    @Autowired
    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /* InitBiner is used when you want to customize the request being sent to the controller */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /* Always use this format for all the dates */
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

        /* Trim the strings */
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping(value = "/list-todos")
    public ModelAndView showListTodos() {
        ModelAndView modelAndView = new ModelAndView("list-todos");
        modelAndView.addObject("name", loggedInUser.getLoggedInUser().getUsername());
        modelAndView.addObject("todos", todoService.retrieveTodos(loggedInUser.getLoggedInUser()));
        return modelAndView;
    }

    @GetMapping(value = "/add-todo")
    public ModelAndView addTodo() {
        ModelAndView modelAndView = new ModelAndView("todo");
        modelAndView.addObject("todo", new Todo()); // in order to use spring mvc tags
        return modelAndView;
    }

    @PostMapping(value = "/add-todo")
    public ModelAndView submitTodo(@Valid Todo todo, BindingResult result) {
        // check for validation errors
        if (result.hasErrors()) {
            return new ModelAndView("todo");
        }
        todo.setUsers(loggedInUser.getLoggedInUser());
        todoService.addTodo(todo);
        // redirect to list-todos, otherwise you should add the model attributes again
        return new ModelAndView("redirect:list-todos");
    }

    @GetMapping(value = "/delete-todo/{id}")
    public ModelAndView deleteToDo (@PathVariable int id) {
        todoService.deleteTodo(id);
        return new ModelAndView("redirect:/list-todos"); //note the slash
    }

    @GetMapping(value = "/update-todo/{id}")
    public ModelAndView updateToDoGet (@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("todo");
        modelAndView.addObject("todo", todoService.retrieveTodo(id));
        return modelAndView;
    }

    @PostMapping(value = "/update-todo/{id}")
    public ModelAndView updateToDoPost (@Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("todo");
        }
        todo.setUsers(loggedInUser.getLoggedInUser());
        todoService.updateTodo(todo);
        return new ModelAndView("redirect:/list-todos");
    }
}