package com.in28minutes.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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
    public String addTodo() {
        return "todo";
    }

    @PostMapping(value = "/add-todo")
    public String submitTodo(@RequestParam String description, ModelMap modelMap) {
        String name = String.valueOf(modelMap.getAttribute("name"));
        todoService.addTodo(name, description, new Date(), false);
        modelMap.clear();   // in order not to pass the session attribute "name" as url parameter
        return "redirect:list-todos"; // redirect to list-todos, otherwise you should add the model attributes again
    }

    @GetMapping(value = "/delete-todo/{id}")
    public String deleteToDo (@PathVariable int id, ModelMap modelMap) {
        todoService.deleteTodo(id);
        modelMap.clear();
        return "redirect:/list-todos"; //note the slash
    }
}