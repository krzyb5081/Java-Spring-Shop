package com.shop.project.service;

import org.springframework.stereotype.Service;

import com.shop.project.dto.UserSession;
import com.shop.project.model.User;
import com.shop.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
	
	private final UserRepository userRepository;
	private final UserSession userSession;
	private final UserService userService;
	
	public boolean loginUser(User user) {
		User resultUser = userService.getByUserName(user.getUserName());
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
	

	public User getUserFromSession() {
		if(userSession.getUserName()==null) {
			return null;
		}
		System.out.println("UserService >> User: "+userSession.getUserName());
		
		return userRepository.findById(userSession.getUserId()).get();
	}
	
}
