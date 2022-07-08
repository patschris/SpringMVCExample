package com.in28minutes.controllers;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import com.in28minutes.service.LoggedInUserService;
import com.in28minutes.service.TodoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
     * @param loggedInUserService
     *          The logged-in user service.
     */
    @Autowired
    public void setLoggedInUser(LoggedInUserService loggedInUserService) {
        this.loggedInUserService = loggedInUserService;
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
    @GetMapping("/list-todos")
    public ModelAndView showListTodos(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("list-todos");
        modelAndView.addObject("name", request.getRemoteUser());
        modelAndView.addObject("todos", todoService.retrieveTodos(loggedInUserService.getLoggedInUser()));
        log.info(className + "." + new Object(){}.getClass().getEnclosingMethod().getName() +
                " - Get request at /list-todos for " + request.getRemoteUser() + " returned " +
                    modelAndView.getModel().get("todos"));
        return modelAndView;
    }

    /**
     * Handles a get request in "/add-todo" path and opens the todo page for the user to add a new todo.
     *
     * @return
     *          The empty form to add a new Todo.
     */
    @GetMapping( "/add-todo")
    public ModelAndView addTodo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("todo");
        /* In order to use spring mvc tags */
        modelAndView.addObject("todo", new Todo(loggedInUserService.getLoggedInUser()));
        log.info(className + "." + new Object(){}.getClass().getEnclosingMethod().getName() +
                " - Get request at /add-todo for " + request.getRemoteUser());
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
    @PostMapping("/add-todo")
    public ModelAndView submitTodo(HttpServletRequest request, @Valid Todo todo, BindingResult result) {
        /* Check for validation errors */
        if (result.hasErrors()) {
            return new ModelAndView("todo");
        }
        Users user = loggedInUserService.getLoggedInUser();
        todo.setUsers(user);
        todoService.addTodo(todo);
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(className + "." + methodName +
                " - Post request at /add-todo for " + request.getRemoteUser() + " added " + todo);
        log.info(className + "." + methodName + " - Redirecting " + request.getRemoteUser() + " to /list-todos");
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
    @GetMapping("/delete-todo/{id}")
    public ModelAndView deleteToDo (HttpServletRequest request, @PathVariable int id) {
        String username = request.getRemoteUser();
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(className + "." + methodName + " - Get request to /delete-todo/" + id + " for user " + username);
        todoService.deleteTodo(id);
        log.info(className + "." + methodName + " - Todo with id " + id + " has been deleted for user " + username);
        log.info(className + "." + methodName + " - Redirecting " + username + " to /list-todos");
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
    @GetMapping("/update-todo/{id}")
    public ModelAndView updateToDoGet (HttpServletRequest request, @PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("todo");
        modelAndView.addObject("todo", todoService.retrieveTodo(id));
        log.info(className + "." + new Object(){}.getClass().getEnclosingMethod().getName() +
                " - Get request at /update-todo/" + id + " for " +
                request.getRemoteUser() + " returned " + modelAndView.getModel().get("todo"));
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
    @PostMapping("/update-todo/{id}")
    public ModelAndView updateToDoPost (HttpServletRequest request, @PathVariable int id, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("todo");
        }
        Users user = loggedInUserService.getLoggedInUser();
        todo.setUsers(user);
        todoService.updateTodo(todo);
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        log.info(className + "." + methodName +
                " - Post request at /update-todo/" + id + " for " + request.getRemoteUser() + " updated " + todo);
        log.info(className + "." + methodName + " - Redirecting " + request.getRemoteUser() + " to /list-todos");
        return new ModelAndView("redirect:/list-todos");
    }
}