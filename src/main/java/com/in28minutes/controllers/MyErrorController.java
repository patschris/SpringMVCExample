package com.in28minutes.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Error controller. Is used to render error.
 *
 * @author Christos Patsouras
 */
@Controller
public class MyErrorController implements ErrorController {
    /**
     * Retrieves the path of the error page.
     *
     * @return
     *          Τhe path to the error page.
     */
    @Override
    public String getErrorPath() {
        return "error";
    }

    /**
     * Handles the "/error" request and returns the error page after an error occurred.
     *
     * @return
     *          The error page.
     */
    @GetMapping("/error")
    public ModelAndView handleError() {
        return new ModelAndView(getErrorPath());
    }
}