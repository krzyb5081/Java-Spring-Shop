package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.shop.project.model.Order;
import com.shop.project.model.OrderPart;
import com.shop.project.model.User;
import com.shop.project.repository.OrderProductRepository;
import com.shop.project.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
	private final ProductService productService;
	private final ShoppingCartService shoppingCartService;
	private final SessionService sessionService;
	
	public String makeOrder() {
		System.out.println("OrderService>>makeOrder()>>");
		if(sessionService.getUserFromSession() == null) {
			System.out.println("You have to login first");
			return "You have to login first";
		}
		if(this.checkAvailability()==false) {
			System.out.println("No product available");
			return "No product available";
		}
		
		List<OrderPart> orderProductList = shoppingCartService.getOrderPartList();
		if(orderProductList.isEmpty()) {
			System.out.println("Cannot make order with empty cart");
			return "Cannot make order with empty cart";
		}
		User user = sessionService.getUserFromSession();
		Order order = new Order();
		
		orderProductList.forEach(orderProduct -> {
			orderProduct.setOrder(order);
			
			long productId = orderProduct.getProduct().getId();
			productService.decreaseProductQuantity(productId, orderProduct.getQuantity());
		});
		
		order.setOrderProductList(orderProductList);
		order.setUser(user);
		order.setStatus("paid");
		
		List<Order> orderList = user.getOrderList();
		orderList.add(order);
		user.setOrderList(orderList);
		
		orderProductRepository.saveAll(orderProductList);
		orderRepository.save(order);
		
		shoppingCartService.clearShoppingCart();
		
		System.out.println("Order made");
		return "Order made";
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
