package com.shop.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.User;
import com.shop.project.service.UserService;

@CrossOrigin(origins = "http://localhost", allowCredentials = "true")
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public String performLogin(@Valid User user) {
		
		if(userService.loginUser(user)) {
			return "loginsuccess";
		} else {
			return "loginfail";
		}
		
	}
	
	@PostMapping("/logout")
	public String logoutUser() {
		userService.logoutUser();
		return "logout";
	}
	
	@PostMapping("/register")
	public String performRegister(@Valid User user) {
		if(userService.registerUser(user)) {
			return "registersuccess";
		} else {
			return "registerfail";
		}
		
	}
	
	@GetMapping("/getSessionUser")
	public User getSessionUser() {
		return userService.getUserBySessionNoPassword();
	}
}
