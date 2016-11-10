package com.techweb.onlinetest.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.techweb.onlinetest.admin.OnlinetestAdminException;




@ControllerAdvice
public class SCBAExceptionHandler {

	private final String ERROR_PAGE = "error";

	private final String ERROR_CODE = "errorCode";
	private final String ERROR_MESSAGE = "errorMessage";

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = OnlinetestAdminException.class)
	public ModelAndView handleApplicationError(Exception e) {

		ModelAndView mav = new ModelAndView(ERROR_PAGE);

		mav.addObject(ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
		mav.addObject(ERROR_MESSAGE, e.getMessage());
		return mav;
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(value = AccessDeniedException.class)
	public ModelAndView handleAccessDeniedException(Exception e) {

		ModelAndView mav = new ModelAndView(ERROR_PAGE);

		mav.addObject(ERROR_CODE, HttpStatus.FORBIDDEN);
		mav.addObject(ERROR_MESSAGE, e.getMessage());
		return mav;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ModelAndView handleIllegalArgumentException(Exception e) {

		ModelAndView mav = new ModelAndView(ERROR_PAGE);

		mav.addObject(ERROR_CODE, HttpStatus.BAD_REQUEST);
		mav.addObject(ERROR_MESSAGE, e.getMessage());
		return mav;
	}

}
