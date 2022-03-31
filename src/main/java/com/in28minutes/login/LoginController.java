package com.in28minutes.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.in28minutes.security.LoggedInUser.getLoggedInUserName;

@Controller
public class LoginController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginPage(ModelMap modelMap) {
        modelMap.addAttribute("name", getLoggedInUserName());
        return "welcome";
    }
}