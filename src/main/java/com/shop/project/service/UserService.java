package com.shop.project.service;

import org.springframework.stereotype.Service;

import com.shop.project.dto.UserDto;
import com.shop.project.model.User;
import com.shop.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	
	private final UserRepository userRepository;
	
	public User getByUserName(String userName) {
		for(User user: userRepository.findAll()) {
			if(user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean registerUser(User user) {
		User resultUser = getByUserName(user.getUserName());

		if(resultUser == null) {
			
			user.setMoney(50);
			user.setType("user");
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	public User dtoToEntity(UserDto userDto) {
		return new User();
	}
	
	public UserDto entityToDto(User user) {
		return new UserDto();
	}
}
