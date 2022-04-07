package com.in28minutes.repositories;

import com.in28minutes.entities.Todo;
import com.in28minutes.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional      // better implementation is transaction per service
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUsers(Users users);
}