package com.in28minutes.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * The Login controller. Handles the "/" request and returns the welcome page after the login.
 *
 * @author Christos Patsouras
 */
@Controller
public class LoginController {

    /**
     * The debug logger.
     */
    private final Logger log = Logger.getLogger("debugLog");

    /**
     * Handles the "/" request and returns the welcome page after the login.
     *
     * @return
     *          The welcome page.
     */
    @GetMapping("/")
    public ModelAndView showLoginPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("name", request.getRemoteUser());
        log.info(getClass().getName() + "." + new Object(){}.getClass().getEnclosingMethod().getName() +
                "- Successful login for " + modelAndView.getModel().get("name"));
        return modelAndView;
    }
}