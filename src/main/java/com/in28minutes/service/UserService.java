package com.in28minutes.service;

import com.in28minutes.entities.Users;
import com.in28minutes.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The User Service. Facilitates the communication between the controller and
 * the persistence layer regarding the User.
 *
 * @author Christos Patsouras
 */
@Service
public class UserService {
    /**
     * The User Repository.
     */
    UserRepository userRepository;

    /**
     * The Log4j Logger.
     */
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * Setter injection for the User Repository.
     *
     * @param userRepository
     *          The User Repository.
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a User object for the specified username.
     *
     * @param username
     *          The username of the logged-in user.
     * @return
     *          The credentials of the user as an instance of the Users' class.
     */
    public Users findUserByUsername (String username) {
        Users user = userRepository.findUserByUsername(username);
        log.info("findUserByUsername() - For " + username + " returned " + user);
        return user;
    }
}