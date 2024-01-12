package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.dto.UserDto;
import com.shop.project.model.User;
import com.shop.project.repository.UserRepository;

public class UserServiceTest {

	private UserService userService;
	
	UserRepository userRepository = Mockito.mock(UserRepository.class);
	
	@BeforeEach
	void setUp() throws Exception{
		userService = new UserService(userRepository);
		
		User savedUser0 = new User(0, "user0", "password0", "user", 150, null);
		User savedUser1 = new User(1, "user1", "password1", "user", 151, null);
		User savedUser2 = new User(2, "user2", "password2", "user", 152, null);
		User savedUser3 = new User(3, "user3", "password3", "user", 153, null);
		
		List<User> userList = new ArrayList<User>();
		userList.add(savedUser0);
		userList.add(savedUser1);
		userList.add(savedUser2);
		userList.add(savedUser3);
		
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		
	}
	
	@Test
	@DisplayName("getByUserName() return User with given username")
	void getByUserName__Return_User_With_Given_Name() {
		
		User savedUser2 = new User(2, "user2", "password2", "user", 152, null);
		User foundUser = userService.getByUserName("user2");
		
		assertEquals(foundUser, savedUser2);
	}
	
	@Test
	@DisplayName("registerUser() return false if username is taken")
	void registerUser__Return_False__If_UserName_Is_Taken() {
		
		User newUser = new User(2, "user2", "password2", "user", 152, null);
		
		assertFalse(userService.registerUser(newUser));
	}
	
	@Test
	@DisplayName("registerUser() return true if username is not taken")
	void registerUser__Return_True__If_UserName_Is_Not_Taken() {
		
		User newUser = new User(2, "untakenName", "password2", "user", 152, null);
		
		assertTrue(userService.registerUser(newUser));
	}

	@Test
	@DisplayName("dtoToEntity() convert UserDto to UserEntity")
	void dtoToEntity__Convert_UserDto_To_UserEntity() {
		UserDto userDto = new UserDto("username1", "password1");
		User userEntity = userService.dtoToEntity(userDto);
		
		assertEquals(userDto.getUserName(), userEntity.getUserName());
		assertEquals(userDto.getPassword(), userEntity.getPassword());
	}
	
	@Test
	@DisplayName("dtoToEntity() convert UserEntity to UserDto")
	void entityToDto__Convert_UserEntity_To_UserDto() {
		User userEntity = new User(0, "username1", "password1", "user", 0, null);
		UserDto userDto = userService.entityToDto(userEntity);
		
		assertEquals(userEntity.getUserName(), userDto.getUserName());
		assertEquals(userEntity.getPassword(), userDto.getPassword());
	}
}
