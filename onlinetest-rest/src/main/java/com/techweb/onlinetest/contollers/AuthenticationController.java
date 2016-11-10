package com.techweb.onlinetest.contollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlinetest.model.OnlinetestUser;
import com.onlinetest.model.User;
import com.techweb.onlinetest.dto.Response;
import com.techweb.onlinetest.security.TokenUtils;



@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	@Qualifier("userDetailServiceImpl")
	private UserDetailsService userDetailsService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> generateAuthentication(@RequestBody User user) {

		LOGGER.info(String.format("Attempting to generate authorization token for user : %s",
				user.getName()));
		// Perform the authentication
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user
						.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-authentication so we can generate token
		OnlinetestUser userDetails = (OnlinetestUser) this.userDetailsService.loadUserByUsername(user.getEmail());
		String token = this.tokenUtils.generateToken(userDetails);

		LOGGER.info(String.format("Authorization token generated for user : %s successfully",
				user.getName()));

		// Return the token
		return ResponseEntity.ok(new Response<String>(HttpStatus.OK.value(),
				"Token generated successfully", token));
	}

}
