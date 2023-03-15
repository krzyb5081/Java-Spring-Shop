package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.dto.UserSession;
import com.shop.project.model.User;
import com.shop.project.repository.UserRepository;

class UserServiceTests {

	UserService userService;
	
	UserRepository userRepository = Mockito.mock(UserRepository.class);
	UserSession userSession = Mockito.mock(UserSession.class);
	
	@BeforeEach
	void initUserServiceTesting() {
		userService = new UserService(userRepository, userSession);
		
		
	}
	
	@Test
	void getByUserNameReturnsProperUser() {
		User savedUser1 = new User(1, "user1", "password1", "user", 151, null);
		User savedUser2 = new User(2, "user2", "password2", "user", 152, null);
		User savedUser3 = new User(3, "user3", "password3", "user", 153, null);
		
		List<User> userList = new ArrayList<User>();
		userList.add(savedUser1);
		userList.add(savedUser2);
		userList.add(savedUser3);
		
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		
		User foundUser = userService.getByUserName("user2");
		
		assertEquals(foundUser, savedUser2);
	}

}
