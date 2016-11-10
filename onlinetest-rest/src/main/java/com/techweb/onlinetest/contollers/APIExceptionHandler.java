package com.techweb.onlinetest.contollers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.techweb.onlinetest.dto.Response;
import com.techweb.onlinetest.exception.ResourceNotFoundExecption;
import com.techweb.onlinetest.exception.SCBException;


@ControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(value = { BadCredentialsException.class, UsernameNotFoundException.class })
	public ResponseEntity<Response<String>> handleAuthenticationFailedException(Exception e) {
		return new ResponseEntity<Response<String>>(new Response<String>(
				HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<Response<String>> handleAccessDeniedException(HttpServletRequest request) {
		return new ResponseEntity<Response<String>>(new Response<String>(
				HttpStatus.FORBIDDEN.value(), String.format("Access denied for path : %s",
						request.getServletPath()), null), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = SCBException.class)
	public ResponseEntity<Response<String>> handleSCBException(Exception e) {
		return new ResponseEntity<Response<String>>(new Response<String>(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage() != null ? e.getMessage()
						: "Internal Server Error.", null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = ResourceNotFoundExecption.class)
	public ResponseEntity<Response<String>> handleResourceNotFoundException(Exception e) {
		return new ResponseEntity<Response<String>>(new Response<String>(
				HttpStatus.NO_CONTENT.value(), e.getMessage() != null ? e.getMessage()
						: "Request resource not found.", null), HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Response<String>> handleIllegalArgumentException(Exception e) {
		return new ResponseEntity<Response<String>>(new Response<String>(
				HttpStatus.BAD_REQUEST.value(), e.getMessage() != null ? e.getMessage()
						: "Bad Request", null), HttpStatus.BAD_REQUEST);
	}

}
