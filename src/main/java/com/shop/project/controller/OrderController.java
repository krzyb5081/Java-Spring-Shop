package com.shop.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.Order;
import com.shop.project.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
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
	public Order makeOrder() {
		return orderService.makeOrder();
	}
	
	
}
