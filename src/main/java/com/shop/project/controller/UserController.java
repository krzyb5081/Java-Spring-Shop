package com.shop.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@GetMapping("/login")
	public String loginForm(User user, Model model) {
		model.addAttribute("user", user);
		return "LoginForm";
	}
	
	@PostMapping("/login")
	public String performLogin(@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "LoginForm";
		}
		
		if(userService.loginUser(user)) {
			return "LoginSuccess";
		} else {
			return "LoginFail";
		}
		
	}
	
	@GetMapping("/logout")
	public String logoutUser() {
		userService.logoutUser();
		return "index";
	}
	
	@GetMapping("/register")
	public String registerForm(User user, Model model) {
		model.addAttribute("user", user);
		return "RegisterForm";
	}
	
	@PostMapping("/register")
	public String performRegister(@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "RegisterForm";
		}
		
		if(userService.registerUser(user)) {
			return "RegisterSuccess";
		} else {
			return "RegisterFail";
		}
		
	}
}
