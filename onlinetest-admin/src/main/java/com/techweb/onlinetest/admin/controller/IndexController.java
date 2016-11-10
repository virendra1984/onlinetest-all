package com.techweb.onlinetest.admin.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
	public String index() {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return "signin";
		}
		boolean isAdmin = false;
		for (GrantedAuthority authority : context.getAuthentication().getAuthorities()) {
			if ("ADMIN".equalsIgnoreCase(authority.getAuthority())) {
				isAdmin = true;
				break;
			}
		}
		return isAdmin ? "admin/dashboard" : "signin";
	}

	@RequestMapping(value = "403", method = RequestMethod.GET)
	public String signin() {
		return "403";
	}

    @RequestMapping(value = "logoutsuccessfully", method = RequestMethod.GET)
    public String logout() {
        return "logout";
    }


}
