package com.in28minutes.repositories;

import com.in28minutes.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional      // better implementation is transaction per service
public interface UserRepository extends JpaRepository<Users, String> {
    Users findUserByUsername(String username);
}