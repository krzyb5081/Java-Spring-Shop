package com.shop.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shop.project.model.Order;
import com.shop.project.model.OrderPart;
import com.shop.project.model.User;
import com.shop.project.repository.OrderPartRepository;
import com.shop.project.repository.OrderRepository;
import com.shop.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderPartRepository orderPartRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductService productService;
	private final ShoppingCartService shoppingCartService;
	private final SessionService sessionService;
	
	public Map<Boolean, Order> makeOrder() {
		Map<Boolean, Order> returnMap = new HashMap<>();
		
		Order order = new Order();
		order.setStatus("not paid");
		
		
		//user logged check
		if(sessionService.getUserFromSession() == null) {
			returnMap.put(false, order);
			return returnMap;
		}
		User user = sessionService.getUserFromSession();
		order.setUser(user);
		
		
		//cart not empty check
		if(shoppingCartService.getOrderPartList() == null){
			returnMap.put(false, order);
			return returnMap;
		}
		
		//bonding orderParts to Order
		List<OrderPart> orderPartList = shoppingCartService.getOrderPartList();
		orderPartList.forEach(orderPart -> {
			orderPart.setOrder(order);
		});
		order.setOrderPartList(orderPartList);
		
		
		//adding new order to users order list
		List<Order> usersOrderList = user.getOrderList();
		if(usersOrderList == null) usersOrderList = new ArrayList<Order>();
		usersOrderList.add(order);
		user.setOrderList(usersOrderList);
		
		
		//products availability check
		if(this.checkAvailability()==false){
			returnMap.put(false, order);
			return returnMap;
		}
		
		//removing products from storage (decreasing quantity)
		orderPartList.forEach(orderPart -> {
			productService.decreaseProductQuantity(orderPart.getProduct().getId(), orderPart.getQuantity());
		});
		
		
		//saving data to repositories
		orderPartRepository.saveAll(orderPartList);
		orderRepository.save(order);
		userRepository.save(user);
		
		//empty the shopping cart
		shoppingCartService.clearShoppingCart();
		
		returnMap.put(true, order);
		return returnMap;
	}
	
	public List<Order> getMyOrders() {
		if(sessionService.getUserFromSession() == null) {
			return new ArrayList<Order>();
		}
		return sessionService.getUserFromSession().getOrderList();
	}
	
	public List<Order> getAllOrders() {
		
		List<Order> orderList = new ArrayList<Order>();
		orderRepository.findAll().forEach(order -> orderList.add(order));
		
		return orderList;
	}
	
	public boolean checkAvailability() {
		List<OrderPart> orderPartList = shoppingCartService.getOrderPartList();
		
		for(OrderPart orderPart: orderPartList) {
			if(orderPart.getQuantity() > orderPart.getProduct().getQuantityAvailable()) return false;
		}
		
		return true;
	}
	
	public double getOrderCost() {
		double cost = 0;
		
		List<OrderPart> orderPartList = shoppingCartService.getOrderPartList();
		
		for(OrderPart orderPart: orderPartList) {
			double price = orderPart.getProduct().getPrice();
			double quantity = orderPart.getQuantity();
			
			cost += price*quantity;
		}
		return cost;
		
	}
	
}
