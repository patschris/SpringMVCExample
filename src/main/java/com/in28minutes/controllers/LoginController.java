package com.in28minutes.controllers;

import com.in28minutes.security.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private LoggedInUser loggedInUser;

    @Autowired
    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    @GetMapping(value = "/")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("name", loggedInUser.getLoggedInUser().getUsername());
        return modelAndView;
    }
}