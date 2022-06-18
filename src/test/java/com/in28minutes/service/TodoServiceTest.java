package com.in28minutes.service;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import com.in28minutes.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;

/**
 * Test class for Todo Service.
 *
 * @author Christos Patsouras
 */
@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    /**
     * Mock for the User entity.
     */
    @Mock
    private static Users users;

    /**
     * Mock for the Todo Repository.
     */
    @Mock
    private TodoRepository todoRepository;

    /**
     * Mock for the Todo Service.
     */
    @InjectMocks
    private TodoService todoService;

    /**
     * A Todo to test.
     */
    private final Todo todo1 = new Todo(1, "This is a test", new Date(), true, users);

    /**
     * Another Todo to test.
     */
    private final Todo todo2 = new Todo(2, "This is another test", new Date(), false, users);

    /**
     * Test for the addTodo method when the entry is successfully saved.
     */
    @Test
    public void addTodoNormal() {
        when(todoRepository.save(any(Todo.class))).thenReturn(null);
        todoService.addTodo(todo1);
    }

    /**
     * Test for the addTodo method when save fails and an exception occurs.
     */
    @Test
    public void addTodoException() {
        doThrow(new RuntimeException()).when(todoRepository).save(any(Todo.class));
        assertThrows(Exception.class, () -> todoService.addTodo(todo1));
    }

    /**
     * Test for the deleteTodo method when the entry is successfully deleted.
     */
    @Test
    public void deleteTodoNormal() {
        doNothing().when(todoRepository).deleteById(ArgumentMatchers.any());
        todoService.deleteTodo(new Random().nextInt(50));
    }

    /**
     * Test for the deleteTodo method when delete fails and an exception occurs in the method.
     */
    @Test
    public void deleteTodoException() {
        doThrow(new RuntimeException()).when(todoRepository).deleteById(any());
        assertThrows(Exception.class, () -> todoService.deleteTodo(0));
    }

    /**
     * Test for the retrieveTodos method when an existing user is given as argument.
     */
    @Test
    public void retrieveTodosNormal() {
        List<Todo> todos = Arrays.asList(todo1, todo2);
        when(todoRepository.findByUsers(any(Users.class))).thenReturn(todos);
        List<Todo> returnedTodos = todoService.retrieveTodos(users);
        assertEquals(todos, returnedTodos);
    }

    /**
     * Test for the retrieveTodos method when a non-existing user is given as argument.
     */
    @Test
    public void retrieveTodosNull() {
        when(todoRepository.findByUsers(any(Users.class))).thenReturn(null);
        List<Todo> returnedTodos = todoService.retrieveTodos(users);
        assertNull(returnedTodos);
    }

    /**
     * Test for the retrieveTodos method when an exception occurs in the method.
     */
    @Test
    public void retrieveTodosException() {
        doThrow(new RuntimeException()).when(todoRepository).findByUsers(any(Users.class));
        assertThrows(Exception.class, () ->  todoService.retrieveTodos(users));
    }

    /**
     * Test for the retrieveTodo method when an id of an existing Todo is given as argument.
     */
    @Test
    public void retrieveTodoNormal() {
        when(todoRepository.findById(anyInt())).thenReturn(Optional.of(todo1));
        Todo returnedTodo = todoService.retrieveTodo(todo1.getId());
        assertEquals(todo1, returnedTodo);
    }

    /**
     * Test for the retrieveTodo method when an id of a non-existing Todo is given as argument.
     */
    @Test
    public void retrieveTodoNull() {
        when(todoRepository.findById(anyInt())).thenReturn(Optional.empty());
        Todo returnedTodo = todoService.retrieveTodo(todo1.getId());
        assertNull(returnedTodo);
    }

    /**
     * Test for the retrieveTodo method when an exception occurs in the method.
     */
    @Test
    public void retrieveTodoException() {
        doThrow(new RuntimeException()).when(todoRepository).findById(anyInt());
        assertThrows(Exception.class, () -> todoService.retrieveTodo(todo1.getId()));
    }

    /**
     * Test for the updateTodo method when the entry is successfully updated.
     */
    @Test
    public void updateTodoNormal() {
        when(todoRepository.save(any(Todo.class))).thenReturn(null);
        todoService.updateTodo(todo1);
    }

    /**
     * Test for the updateTodo method when update fails.
     */
    @Test
    public void updateTodoException() {
        doThrow(new RuntimeException()).when(todoRepository).save(any(Todo.class));
        assertThrows(Exception.class, () -> todoService.updateTodo(todo1));
    }

    /**
     * Test for the getTodoMaxId method.
     */
    @Test
    void getTodoMaxIdNormal() {
        when(todoRepository.getMaxId()).thenReturn(1000);
        assertEquals(1000, todoService.getTodoMaxId());
    }

    /**
     * Test for the getTodoMaxId method when an exception occurs in the method.
     */
    @Test
    void getTodoMaxIdNormalException() {
        doThrow(new RuntimeException()).when(todoRepository).getMaxId();
        assertThrows(Exception.class, () -> todoService.getTodoMaxId());
    }
}