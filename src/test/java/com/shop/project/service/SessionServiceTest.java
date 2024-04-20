package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.dto.Session;
import com.shop.project.model.User;

public class SessionServiceTest {

	private SessionService sessionService;
	
	private Session session = Mockito.mock(Session.class);
	private UserService userService = Mockito.mock(UserService.class);
	private User user = Mockito.mock(User.class);
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		sessionService = new SessionService(session, userService);
		
		User user0 = new User(0, "user0", "password0", "user", 150, null);
		User user1 = new User(1, "user1", "password1", "user", 151, null);
		User user2 = new User(2, "user2", "password2", "user", 152, null);
		User user3 = new User(3, "user3", "password3", "user", 153, null);
		
		
		Mockito.doAnswer(arguments -> {
			return user0;
		}).when(userService).getByUserName(user0.getUserName());
		Mockito.doAnswer(arguments -> {
			return user1;
		}).when(userService).getByUserName(user1.getUserName());
		Mockito.doAnswer(arguments -> {
			return user2;
		}).when(userService).getByUserName(user2.getUserName());
		Mockito.doAnswer(arguments -> {
			return user3;
		}).when(userService).getByUserName(user3.getUserName());
		Mockito.doAnswer(arguments -> {
			return null;
		}).when(userService).getByUserName("notRegisteredUser");
		
		Mockito.when(user.getUserName()).thenReturn("user1");
		Mockito.when(user.getPassword()).thenReturn("password1");
		
	}

	@Test
	void LoginUser__Registered_User__Set_Id_And_Username() {
		User registeredUser = new User(1, "user1", "password1", null, 0, null);
		
		
		Mockito.doAnswer(arguments -> {
			Long userId = arguments.getArgument(0);
			assertEquals(registeredUser.getId(), userId);
			return null;
		}).when(session).setUserId(registeredUser.getId());
		
		Mockito.doAnswer(arguments -> {
			String userName = arguments.getArgument(0);
			assertEquals(registeredUser.getUserName(), userName);
			return null;
		}).when(session).setUserName(registeredUser.getUserName());
		
		Session session = sessionService.loginUser(registeredUser);
		
		assertEquals(session.getUserId(), registeredUser.getId());
	}
	
	@Test
	void LoginUser__Unregistered_User__Clean_Id_And_Username() {
		User notRegisteredUser = new User(5, "unregisteredUser", "password5", null, 0, null);
		
		Mockito.doAnswer(arguments -> {
			Long userId = arguments.getArgument(0);
			assertNotEquals(notRegisteredUser.getId(), userId);
			return null;
		}).when(session).setUserId(0);
		
		Mockito.doAnswer(arguments -> {
			String userName = arguments.getArgument(0);
			assertNotEquals(notRegisteredUser.getUserName(), userName);
			return null;
		}).when(session).setUserName(null);
		
		sessionService.loginUser(notRegisteredUser);
	}

	@Test
	void LogoutUser__Clean_Id_And_Username() {
		Mockito.doAnswer(arguments -> {
			Long userId = arguments.getArgument(0);
			assertEquals(0L, userId);
			return null;
		}).when(session).setUserId(0);
		
		Mockito.doAnswer(arguments -> {
			String userName = arguments.getArgument(0);
			assertEquals(null, userName);
			return null;
		}).when(session).setUserName(null);
		
		sessionService.logoutUser();
	}

	@Test
	void GetUserFromSession__Unregistered_User__Return_Null() {
		Mockito.when(session.getUserName()).thenReturn(null);
		
		assertEquals(null, sessionService.getUserFromSession());
	}
	
	@Test
	void GetUserFromSession__Registered_User__Return_Found_User() {
		User registeredUser = new User(2, "user2", "password2", "user", 152, null);
		Mockito.when(session.getUserName()).thenReturn(registeredUser.getUserName());
		
		assertEquals(registeredUser, sessionService.getUserFromSession());
		
	}

}
