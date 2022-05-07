package com.in28minutes.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

/**
 * The exception controller. Is called after an exception raised in a Controller.
 * Controller Advice means for all the controllers in the application.
 *
 * @author Christos Patsouras
 * @version 1
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController {
	/**
	 * The logger.
	 */
	private final Log logger = LogFactory.getLog(ExceptionController.class);

	/**
	 * Default exception handler for every exception raised.
	 *
	 * @param req
	 * 			The incoming request.
	 * @param exception
	 * 			The exception raised.
	 * @return
	 * 			The error page.
	 */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception exception) {
		logger.error("Request: " + req.getRequestURL() + " raised " + exception);
		return new ModelAndView("error");
	}
}