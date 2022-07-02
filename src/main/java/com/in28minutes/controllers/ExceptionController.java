package com.in28minutes.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * The exception controller. Is called after an exception raised in a Controller.
 * Controller Advice means for all the controllers in the application.
 *
 * @author Christos Patsouras
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController {
	/**
	 * The debug logger.
	 */
	private final Logger log = Logger.getLogger("debugLog");

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
		Writer buffer = new StringWriter();
		PrintWriter pw = new PrintWriter(buffer);
		exception.printStackTrace(pw);
		log.error("Request: " + req.getRequestURL() + " raised an exception: "
				+ exception.getMessage() + ".\n" + buffer);
		return new ModelAndView("error");
	}
}