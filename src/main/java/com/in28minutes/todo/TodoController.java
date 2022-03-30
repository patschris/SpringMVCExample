package com.in28minutes.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@SessionAttributes("name")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping(value = "/list-todos")
    public String showListTodos(ModelMap modelMap) {
        modelMap.addAttribute("todos", todoService.retrieveTodos("in28Minutes"));
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
        String name = String.valueOf(modelMap.getAttribute("name"));
        todoService.addTodo(name, todo.getDesc(), new Date(), false);
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
        todo.setTargetDate(new Date());
        todo.setUser(String.valueOf(modelMap.getAttribute("name")));
        todoService.updateTodo(todo);
        modelMap.clear();   // in order not to pass the session attribute "name" as url parameter
        return "redirect:/list-todos";
    }
}