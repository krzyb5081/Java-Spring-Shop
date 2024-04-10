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
		order.setStatus("not paid");
		
		
		//user logged check
		if(sessionService.getUserFromSession() == null) return order;
		User user = sessionService.getUserFromSession();
		order.setUser(user);
		
		
		//cart not empty check
		if(shoppingCartService.getOrderPartList() == null) return order;
		//bonding orderParts to Order
		List<OrderPart> orderPartList = shoppingCartService.getOrderPartList();
		orderPartList.forEach(orderPart -> {
			orderPart.setOrder(order);
		});
		order.setOrderPartList(orderPartList);
		
		//TODO: user.getOrderList == null
		//adding new order to users order list
		List<Order> usersOrderList = user.getOrderList();
		if(usersOrderList == null) usersOrderList = new ArrayList<Order>();
		usersOrderList.add(order);
		user.setOrderList(usersOrderList);
		
		
		//products availability check
		if(this.checkAvailability()==false) return order;
		//removing products from storage (decreasing quantity)
		orderPartList.forEach(orderPart -> {
			productService.decreaseProductQuantity(orderPart.getProduct().getId(), orderPart.getQuantity());
		});
		
		
		//saving data to repositories
		orderPartRepository.saveAll(orderPartList);
		orderRepository.save(order);
		userRepository.save(user);
		
		
		//status just for check (not saved in database)
		order.setStatus("Succesfully made");
		shoppingCartService.clearShoppingCart();
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
