package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.Order;
import com.shop.project.service.OrderService;
import com.shop.project.service.UserService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	@GetMapping("/showOrders")
	public List<Order> showOrders() {
		return orderService.getAllOrders();
	}
	
	@GetMapping("/showMyOrders")
	public List<Order> showMyOrders() {
		return orderService.getMyOrders();
	}
	
	@GetMapping("/getOrderCost")
	public Double getOrderCost() {
		return orderService.getOrderCost();
	}
	
	@GetMapping("/makeOrder")
	public String makeOrder() {
		return orderService.makeOrder();
	}
	
	
}
