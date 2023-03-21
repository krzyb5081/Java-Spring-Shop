package com.shop.project.service;

import org.springframework.stereotype.Service;

import com.shop.project.dto.UserSession;
import com.shop.project.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
	
	private final UserSession userSession;
	private final UserService userService;
	
	public UserSession loginUser(User user) {
		this.logoutUser();
		User resultUser = userService.getByUserName(user.getUserName());
		if( (resultUser != null) && (resultUser.getPassword().equals(user.getPassword())==true) ) {
			userSession.setUserId(resultUser.getId());
			userSession.setUserName(resultUser.getUserName());
		}
		return userSession;
	}
	
	public UserSession logoutUser() {
		userSession.setUserId(0);
		userSession.setUserName(null);
		return userSession;
	}
	

	public User getUserFromSession() {
		if(userSession.getUserName()==null) {
			return null;
		}
		return userService.getByUserName(userSession.getUserName());
	}
	
}
