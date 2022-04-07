package com.in28minutes.security;

import com.in28minutes.entities.Users;
import com.in28minutes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoggedInUser {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Users getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails ?
                    userService.findUserByUsername(((UserDetails) principal).getUsername()):
                    null;
    }
}