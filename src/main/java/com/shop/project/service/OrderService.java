package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
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
	
	public Order makeOrder() {
		Order order = new Order();
		User user = sessionService.getUserFromSession();
		
		//safety checks
		if(sessionService.getUserFromSession() == null) {
			//return "You have to login first";
			return order;
		}
		if(shoppingCartService.getOrderPartList().isEmpty()) {
			//return "Cannot make order with empty cart";
			return order;
		}
		if(this.checkAvailability()==false) {
			//return "No product available";
			return order;
		}
		
		//bonding Order to OrderParts
		List<OrderPart> orderPartList = shoppingCartService.getOrderPartList();
		orderPartList.forEach(orderPart -> {
			orderPart.setOrder(order);
			productService.decreaseProductQuantity(orderPart.getProduct().getId(), orderPart.getQuantity());
		});
		
		//setting Order fields
		order.setOrderPartList(orderPartList);
		order.setUser(user);
		order.setStatus("not paid");
		
		//bonding new Order to User
		List<Order> orderList = user.getOrderList();
		orderList.add(order);
		user.setOrderList(orderList);
		
		//saving data to repositories
		orderPartRepository.saveAll(orderPartList);
		orderRepository.save(order);
		userRepository.save(user);
		orderPartList.forEach(orderPart -> {
			productService.decreaseProductQuantity(orderPart.getProduct().getId(), orderPart.getQuantity());
		});
		
		shoppingCartService.clearShoppingCart();
		
		System.out.println("Order made");
		//return "Order made";
		return order;
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
		List<OrderPart> cartProductList = shoppingCartService.getOrderPartList();
		
		for(OrderPart cartProduct: cartProductList) {
			
			long productId = cartProduct.getProduct().getId();
			int quantity = cartProduct.getQuantity();
			
			int quantityAvailable = productService.getProductById(productId).getQuantityAvailable();
			
			if(quantityAvailable < quantity) {
				return false;
			}
		}
		
		return true;
	}
	
	public double getOrderCost() {
		double cost = 0;
		
		List<OrderPart> cartOrderProductList = shoppingCartService.getOrderPartList();
		
		for(OrderPart cartOrderProduct: cartOrderProductList) {
			double price = cartOrderProduct.getProduct().getPrice();
			int quantity = cartOrderProduct.getQuantity();
			
			cost += price*quantity;
		}
		return cost;
		
	}
	
}
