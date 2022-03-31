package com.in28minutes.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.in28minutes.security.LoggedInUser.getLoggedInUserName;

@Controller
public class TodoController {

    public TodoService todoService;

    @Autowired
    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    /* InitBiner is used when you want to customize the request being sent to the controller */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /* Always use this format for all the dates */
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));


        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping(value = "/list-todos")
    public String showListTodos(ModelMap modelMap) {
        modelMap.addAttribute("name", getLoggedInUserName());
        modelMap.addAttribute("todos", todoService.retrieveTodos(getLoggedInUserName()));
        return "list-todos";
    }

    @GetMapping(value = "/add-todo")
    public String addTodo(ModelMap modelMap) {
        modelMap.addAttribute("todo", new Todo()); // in order to use spring mvc tags
        return "todo";
    }

    @PostMapping(value = "/add-todo")
    public String submitTodo(ModelMap modelMap, @Valid Todo todo, BindingResult result) {
        // check for validation errors
        if (result.hasErrors()) {
            return "todo";
        }
        todoService.addTodo(getLoggedInUserName(), todo.getDesc(), new Date(), false);
        modelMap.clear();   // in order not to pass the session attribute "name" as url parameter
        return "redirect:list-todos"; // redirect to list-todos, otherwise you should add the model attributes again
    }

    @GetMapping(value = "/delete-todo/{id}")
    public String deleteToDo (@PathVariable int id, ModelMap modelMap) {
        todoService.deleteTodo(id);
        modelMap.clear();
        return "redirect:/list-todos"; //note the slash
    }

    @GetMapping(value = "/update-todo/{id}")
    public String updateToDoGet (ModelMap modelMap, @PathVariable int id) {
        Todo todo = todoService.retrieveTodo(id);
        modelMap.clear();
        modelMap.addAttribute("todo", todo);
        return "todo";
    }

    @PostMapping(value = "/update-todo/{id}")
    public String updateToDoPost (ModelMap modelMap, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        todo.setUser(getLoggedInUserName());
        todoService.updateTodo(todo);
        modelMap.clear();   // in order not to pass the session attribute "name" as url parameter
        return "redirect:/list-todos";
    }
}