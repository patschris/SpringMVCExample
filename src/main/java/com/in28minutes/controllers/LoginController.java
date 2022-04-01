package com.in28minutes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.in28minutes.security.LoggedInUser.getLoggedInUserName;

@Controller
public class LoginController {

    @GetMapping(value = "/")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("name", getLoggedInUserName());
        return modelAndView;
    }
}