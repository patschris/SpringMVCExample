package com.in28minutes.controllers;

import com.in28minutes.security.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Login controller. Handles the "/" request and returns the welcome page after the login.
 *
 * @author Christos Patsouras
 * @version 1
 */
@Controller
public class LoginController {
    /**
     * The logged-in user.
     */
    private LoggedInUser loggedInUser;

    /**
     * Setter injection for the logged-in user.
     *
     * @param loggedInUser
     *          The logged-in user.
     */
    @Autowired
    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Handles the "/" request and returns the welcome page after the login.
     *
     * @return
     *          The welcome page.
     */
    @GetMapping(value = "/")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("name", loggedInUser.getLoggedInUser().getUsername());
        return modelAndView;
    }
}