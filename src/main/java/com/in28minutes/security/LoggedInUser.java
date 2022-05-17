package com.in28minutes.security;

import com.in28minutes.entities.Users;
import com.in28minutes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The Logged-in user's class.
 *
 * @author Christos Patsouras
 */
@Component
public class LoggedInUser {
    /**
     * The User Service.
     */
    private UserService userService;

    /**
     * Setter injection for the User Service.
     *
     * @param userService
     *          The User Service.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Finds logged-in user from context.
     *
     * @return
     *          The logged-in user.
     */
    public Users getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails ?
                    userService.findUserByUsername(((UserDetails) principal).getUsername()):
                    null;
    }
}