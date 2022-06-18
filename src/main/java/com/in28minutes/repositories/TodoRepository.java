package com.in28minutes.repositories;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Todo Repository.
 * Describes all the methods transact with the Todo table in the DB.
 *
 * @author Christos Patsouras
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    /**
     * Retrieves all the Todos of the specified user.
     *
     * @param users
     *          The logged-in user.
     * @return
     *      A list containing all the Todos of the specified user.
     */
    List<Todo> findByUsers(Users users);

    /**
     * Retrieves the largest id from the Todo table/
     *
     * @return
     *      The largest id between all todos
     */
    @Query(value = "select max(t.id) from Todo t")
    Integer getMaxId();
}