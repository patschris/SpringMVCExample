package com.in28minutes.repositories;

import com.in28minutes.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The User Repository.
 * Describes all the methods transact with the User table in the DB.
 *
 * @author Christos Patsouras
 * @version 1
 */
@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    /**
     * Retrieves a User object for the specified username.
     *
     * @param username
     *          The username of the logged-in user.
     * @return
     *          The credentials of the user as an instance of the Users' class.
     */
    Users findUserByUsername(String username);
}