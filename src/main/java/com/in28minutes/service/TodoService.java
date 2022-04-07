package com.in28minutes.service;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import com.in28minutes.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    TodoRepository todoRepository;

    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> retrieveTodos(Users users) {
        return todoRepository.findByUsers(users);
    }

    public Optional<Todo> retrieveTodo(int id) {
        return todoRepository.findById(id);
    }

    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }
}