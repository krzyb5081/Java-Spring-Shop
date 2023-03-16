package com.shop.project.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.User;
import com.shop.project.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public String registerUser(@Valid User user) {
		System.out.println("\nUserControl.java:");
		System.out.println(user.getUserName()+"\n"+user.getPassword());
		if(userService.registerUser(user)) {
			System.out.println("register succes");
			return "registersuccess";
		} else {
			System.out.println("register fail");
			return "registerfail";
		}
	}

}
