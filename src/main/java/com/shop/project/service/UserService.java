package com.shop.project.service;

import org.springframework.stereotype.Service;

import com.shop.project.dto.UserSession;
import com.shop.project.model.User;
import com.shop.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	
	private final UserRepository userRepository;
	private final UserSession userSession;
	
	
	
	public User getByUserName(String userName) {
		for(User user: userRepository.findAll()) {
			if(user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean loginUser(User user) {
		User resultUser = getByUserName(user.getUserName());
		if( (resultUser != null) && (resultUser.getPassword().equals(user.getPassword())==true) ) {
			userSession.setUserId(resultUser.getId());
			userSession.setUserName(resultUser.getUserName());
			return true;
		}
		return false;
	}
	
	public void logoutUser() {
		userSession.setUserId(0);
		userSession.setUserName(null);
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
	
	public User getUserBySession() {
		if(userSession.getUserName()==null) {
			return null;
		}
		System.out.println("UserService >> User: "+userSession.getUserName());
		
		return userRepository.findById(userSession.getUserId()).get();
	}
}
