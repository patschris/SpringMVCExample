package com.in28minutes.service;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import com.in28minutes.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Todo Service. Facilitates the communication between the controller and
 * the persistence layer regarding the Todo.
 *
 * @author Christos Patsouras
 */
@Service
public class TodoService {
    /**
     * The Todo Repository.
     */
    TodoRepository todoRepository;

    /**
     * Setter injection for the Todo Repository.
     *
     * @param todoRepository
     *          The Todo Repository.
     */
    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Saves a new Todo in the db.
     *
     * @param todo
     *          The Todo to be added.
     */
    @Transactional( propagation = Propagation.REQUIRED,
                    isolation = Isolation.READ_COMMITTED,
                    rollbackFor = Exception.class)
    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }

    /**
     * Deletes a Todo from the db specified by its id.
     *
     * @param id
     *          The id of the Todo to be deleted.
     */
    @Transactional( propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void deleteTodo(int id) {
        todoRepository.deleteById(id);
    }

    /**
     * Retrieves all the Todos of the specified user.
     *
     * @param users
     *          The logged-in user.
     * @return
     *         A list containing all the Todos of the specified user.
     */
    @Transactional( propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class,
            readOnly = true)
    public List<Todo> retrieveTodos(Users users) {
        return todoRepository.findByUsers(users);
    }

    /**
     * Retrieves the Todo specified by its id.
     *
     * @param id
     *          The id of the Todo to be searched.
     * @return
     *          The Todo that has the specified id.
     */
    @Transactional( propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class,
            readOnly = true)
    public Todo retrieveTodo(int id) {
        return Optional.of(todoRepository.findById(id)).get().orElse(null);
    }

    /**
     * Updates a Todo in the db.
     *
     * @param todo
     *          The Todo to be updated.
     */
    @Transactional( propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    @Transactional( propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class,
            readOnly = true)
    public Integer getTodoMaxId() {
        return todoRepository.getMaxId();
    }
}