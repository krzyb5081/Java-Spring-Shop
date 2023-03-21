package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.dto.UserSession;
import com.shop.project.model.User;

class SessionServiceTest {

	private SessionService sessionService;
	
	private UserSession userSession = Mockito.spy(new UserSession());
	private UserService userService = Mockito.mock(UserService.class);
	
	@BeforeEach
	void setUp() throws Exception {
		
		sessionService = new SessionService(userSession, userService);
		
		User user0 = new User(0, "user0", "password0", "user", 150, null);
		User user1 = new User(1, "user1", "password1", "user", 151, null);
		User user2 = new User(2, "user2", "password2", "user", 152, null);
		User user3 = new User(3, "user3", "password3", "user", 153, null);
		
		Mockito.when(userService.getByUserName("user0")).thenReturn(user0);
		Mockito.when(userService.getByUserName("user1")).thenReturn(user1);
		Mockito.when(userService.getByUserName("user2")).thenReturn(user2);
		Mockito.when(userService.getByUserName("user3")).thenReturn(user3);
		Mockito.when(userService.getByUserName("notRegisteredUser")).thenReturn(null);
		
	}

	@Test
	void testLoginUser_registered_user_should_be_logged() {
		User registeredUser = new User(1, "user1", "password1", null, 0, null);
		
		Mockito.doAnswer(arguments -> {
			Long userId = arguments.getArgument(0);
			assertEquals(1L, userId);
			return null;
		}).when(userSession).setUserId(registeredUser.getId());
		
		Mockito.doAnswer(arguments -> {
			String userName = arguments.getArgument(0);
			assertEquals("user1", userName);
			return null;
		}).when(userSession).setUserName(registeredUser.getUserName());
		
		sessionService.loginUser(registeredUser);
	}
	
	@Test
	void testLoginUser_unregistered_user_should_not_be_logged() {
		User notRegisteredUser = new User(5, "user5", "password5", null, 0, null);
		
		Mockito.doAnswer(arguments -> {
			Long userId = arguments.getArgument(0);
			assertNotEquals(notRegisteredUser.getId(), userId);
			return null;
		}).when(userSession).setUserId(0);
		
		Mockito.doAnswer(arguments -> {
			String userName = arguments.getArgument(0);
			assertNotEquals(notRegisteredUser.getUserName(), userName);
			return null;
		}).when(userSession).setUserName(null);
		
		sessionService.loginUser(notRegisteredUser);
	}

	@Test
	void testLogoutUser() {
		
	}

	@Test
	void testGetUserFromSession() {
		
	}

}
