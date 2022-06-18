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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.in28minutes.configuration.DateFormat.DATE_PATTERN;

/**
 * The Todo controller. Handles all the requests for the todo, except for the RESTful ones.
 *
 * @author Christos Patsouras
 */
@Controller
public class TodoController {
    /**
     * The Todo Service.
     */
    private TodoService todoService;

    /**
     * The logged-in user.
     */
    private LoggedInUser loggedInUser;

    /**
     * Setter injection for the Todo Service.
     *
     * @param todoService
     *          The Todo Service.
     */
    @Autowired
    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
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
     *  InitBinder is used when you want to customize the request being sent to the controller.
     *  This method customizes the date format and trims the strings.
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /* Always use this format for all the dates */
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));

        /* Trim the strings */
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    /**
     * Handles a get request in "/list-todos" path.
     *
     * @return
     *          A list of all todos for the logged-in user.
     */
    @GetMapping(value = "/list-todos")
    public ModelAndView showListTodos() {
        ModelAndView modelAndView = new ModelAndView("list-todos");
        modelAndView.addObject("name", loggedInUser.getLoggedInUser().getUsername());
        modelAndView.addObject("todos", todoService.retrieveTodos(loggedInUser.getLoggedInUser()));
        return modelAndView;
    }

    /**
     * Handles a get request in "/add-todo" path and opens the todo page for the user to add a new todo.
     *
     * @return
     *          The empty form to add a new Todo.
     */
    @GetMapping(value = "/add-todo")
    public ModelAndView addTodo() {
        ModelAndView modelAndView = new ModelAndView("todo");
        /* In order to use spring mvc tags */
        modelAndView.addObject("todo", new Todo(loggedInUser.getLoggedInUser()));
        return modelAndView;
    }

    /**
     * Handles a post request in "/add-todo" path and adds a new Todo for the logged-in user.
     *
     * @param todo
     *          The newly created Todo.
     * @param result
     *          The binding result of the validations.
     * @return
     *          A Todo list that contains the newly created Todo, if the validations are successful, otherwise the todo
     *          page containing the validation errors.
     */
    @PostMapping(value = "/add-todo")
    public ModelAndView submitTodo(@Valid Todo todo, BindingResult result) {
        /* Check for validation errors */
        if (result.hasErrors()) {
            return new ModelAndView("todo");
        }
        todo.setUsers(loggedInUser.getLoggedInUser());
        todoService.addTodo(todo);
        /* Redirect to list-todos, otherwise you should add the model attributes again */
        return new ModelAndView("redirect:list-todos");
    }

    /**
     * Handles a get request in "/delete-todo" path and deletes the Todo with the given id.
     *
     * @param id
     *          The id of the Todo to be deleted as path variable.
     * @return
     *          A Todo list that doesn't contain the deleted Todo.
     */
    @GetMapping(value = "/delete-todo/{id}")
    public ModelAndView deleteToDo (@PathVariable int id) {
        todoService.deleteTodo(id);
        return new ModelAndView("redirect:/list-todos"); //note the slash
    }

    /**
     * Handles a get request in "/update-todo/{id}" path and retrieves the todo to be updated.
     *
     * @param id
     *          The id of the Todo to be updated as path variable.
     * @return
     *          The details of the Todo to be updated.
     */
    @GetMapping(value = "/update-todo/{id}")
    public ModelAndView updateToDoGet (@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("todo");
        modelAndView.addObject("todo", todoService.retrieveTodo(id));
        return modelAndView;
    }

    /**
     * Handles a post request in "/update-todo" path and updates the Todo with the given id.
     *
     * @param todo
     *          The id of the Todo to be updated as path variable.
     * @param result
     *          The binding result of the validations.
     * @return
     *          A Todo list that contains the updated Todo.
     */
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