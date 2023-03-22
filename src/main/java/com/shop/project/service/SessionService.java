package com.shop.project.service;

import org.springframework.stereotype.Service;

import com.shop.project.dto.Session;
import com.shop.project.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
	
	private final Session session;
	private final UserService userService;
	
	public Session loginUser(User user) {
		this.logoutUser();
		User resultUser = userService.getByUserName(user.getUserName());
		if( (resultUser != null) && (resultUser.getPassword().equals(user.getPassword())==true) ) {
			session.setUserId(resultUser.getId());
			session.setUserName(resultUser.getUserName());
		}
		return session;
	}
	
	public Session logoutUser() {
		session.setUserId(0);
		session.setUserName(null);
		return session;
	}
	

	public User getUserFromSession() {
		if(session.getUserName()==null) {
			return null;
		}
		return userService.getByUserName(session.getUserName());
	}
	
}
