package com.techweb.onlinetest.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techweb.onlinetest.dto.Response;

@Component("accessDeniedHandler")
public class OnlenetestAccessDeniedHandler implements AccessDeniedHandler {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse servletResponse,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		Response<String> response = new Response<String>(HttpStatus.FORBIDDEN.value(),
				"Access denied for resource :" + request.getRequestURI(), null);

		try (PrintWriter writer = servletResponse.getWriter()) {
			writer.write(OBJECT_MAPPER.writeValueAsString(response));
			writer.flush();
		}

	}
}
