package com.shop.project.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.User;
import com.shop.project.service.SessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SessionController {
	
	private final SessionService sessionService;

	@PostMapping("/login")
	public String performLogin(@Valid User user) {
		
		if(sessionService.loginUser(user)) {
			return "loginsuccess";
		} else {
			return "loginfail";
		}
		
	}
	
	@PostMapping("/logout")
	public String logoutUser() {
		sessionService.logoutUser();
		return "logout";
	}
	
	@GetMapping("/getSessionUser")
	public User getSessionUser() {
		return sessionService.getUserFromSession();
	}
}
