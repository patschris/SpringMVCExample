package com.in28minutes.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;

@Controller
@SessionAttributes("name")
public class TodoController {

    @Autowired
    TodoService todoService;

    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String showListTodos(ModelMap modelMap) {
        modelMap.addAttribute("todos", todoService.retrieveTodos("in28Minutes"));
        return "list-todos";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String addTodo() {
        return "todo";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String submitTodo(@RequestParam String description, ModelMap modelMap) {
        String name = String.valueOf(modelMap.getAttribute("name"));
        todoService.addTodo(name, description, new Date(), false);
        modelMap.clear();   // gia na min pernaei to name san parametro sto url
        return "redirect:list-todos"; // redirect gia na min ksanafortwnw to modelmap
    }
}