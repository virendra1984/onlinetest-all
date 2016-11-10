package com.techweb.onlinetest.admin.config;


import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.techweb.onlinetest.admin.OnlinetestAdminException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("classpath:user.properties")
	private Resource userProperties;

	private UserDetailsService userDetailService;

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("_spring_security_remember_me",
				getInMemoryUserDetailService());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(getInMemoryUserDetailService());//.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		csrf().disable()
		.authorizeRequests().antMatchers("/", "/favicon.ico", "/resources/**").permitAll()
//				.anyRequest().authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.and()
				.formLogin()
					.loginPage("/")
					.permitAll()
					.loginProcessingUrl("/authenticate")
						.usernameParameter("j_username")
						.passwordParameter("j_password")
						.defaultSuccessUrl("/admin/dashboard")
					.and()
					.logout()
						.logoutUrl("/logout")
						.clearAuthentication(true)
						.invalidateHttpSession(true)
						.logoutSuccessUrl("/logoutsuccessfully")
						.permitAll()
					.and()
					.rememberMe()
						.rememberMeServices(rememberMeServices())
							.key("_spring_security_remember_me")
					.and()
					.exceptionHandling()
		                .accessDeniedPage("/403");
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private UserDetailsService getInMemoryUserDetailService() {

		if (userDetailService == null) {

			Properties users = new Properties();
			try {
				users.load(userProperties.getInputStream());
				userDetailService = new InMemoryUserDetailsManager(users);
			} catch (IOException e) {
				throw new OnlinetestAdminException("Error while reading user property files.", e);
			}
		}
		return userDetailService;
	}
}