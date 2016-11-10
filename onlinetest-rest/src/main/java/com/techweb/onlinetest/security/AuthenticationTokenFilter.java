package com.techweb.onlinetest.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techweb.onlinetest.dto.Response;



public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Value("${scw.token.header}")
	private String tokenHeader;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	@Qualifier("userDetailServiceImpl")
	private UserDetailsService userDetailsService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(this.tokenHeader);

		if (!StringUtils.isEmpty(authToken)
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				String username = this.tokenUtils.getUsernameFromToken(authToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				writeErrorToResponse(e, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private void writeErrorToResponse(Exception e, ServletResponse servletResponse)
			throws IOException {

		Response<String> response = new Response<String>(HttpStatus.UNAUTHORIZED.value(),
				e.getMessage(), null);

		try (PrintWriter writer = servletResponse.getWriter()) {
			writer.write(OBJECT_MAPPER.writeValueAsString(response));
			writer.flush();
		}

	}
}
