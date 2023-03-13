package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.project.model.Order;
import com.shop.project.model.OrderPart;
import com.shop.project.model.User;
import com.shop.project.repository.OrderProductRepository;
import com.shop.project.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderProductRepository orderProductRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	public String makeOrder() {
		System.out.println("OrderService>>makeOrder()>>");
		if(userService.getUserBySession() == null) {
			System.out.println("You have to login first");
			return "You have to login first";
		}
		if(orderService.checkAvailability()==false) {
			System.out.println("No product available");
			return "No product available";
		}
		
		List<OrderPart> orderProductList = shoppingCartService.getOrderProductList();
		if(orderProductList.isEmpty()) {
			System.out.println("Cannot make order with empty cart");
			return "Cannot make order with empty cart";
		}
		User user = userService.getUserBySession();
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
		if(userService.getUserBySession() == null) {
			return new ArrayList<Order>();
		}
		return userService.getUserBySession().getOrderList();
	}
	
	public List<Order> getAllOrders() {
		
		List<Order> orderList = new ArrayList<Order>();
		orderRepository.findAll().forEach(order -> orderList.add(order));
		
		return orderList;
	}
	
	public boolean checkAvailability() {
		List<OrderPart> cartProductList = shoppingCartService.getOrderProductList();
		
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
		
		List<OrderPart> cartOrderProductList = shoppingCartService.getOrderProductList();
		
		for(OrderPart cartOrderProduct: cartOrderProductList) {
			double price = cartOrderProduct.getProduct().getPrice();
			int quantity = cartOrderProduct.getQuantity();
			
			cost += price*quantity;
		}
		return cost;
		
	}
	
}
