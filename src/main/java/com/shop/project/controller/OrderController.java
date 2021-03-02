package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.Order;
import com.shop.project.service.OrderService;
import com.shop.project.service.UserService;

@RestController
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	@GetMapping("/showOrders")
	public List<Order> showOrders() {
		
		if(userService.getSessionUserName()==null) {
			return null;
		}
		
		if(userService.getUserBySession().getType().equals("seller")) {
			return orderService.getAllOrders();
		}
		else {
			return orderService.getMyOrders();
		}
	}
}
