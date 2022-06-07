package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.project.model.Order;
import com.shop.project.model.OrderProduct;
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
	
	public void makeOrder() {
		
		List<OrderProduct> orderProductList = shoppingCartService.getOrderProductList();
		if(orderProductList.isEmpty()) {
			return;
		}
		User user = userService.getUserBySessionNoPassword();
		Order order = new Order();
		
		orderProductList.forEach(orderProduct -> {
			orderProduct.setOrder(order);
			
			long productId = orderProduct.getProduct().getId();
			productService.decreaseProductQuantity(productId, orderProduct.getQuantity());
		});
		
		order.setOrderProductList(orderProductList);
		order.setUser(user);
		order.setStatus("paid");
		
		orderProductRepository.saveAll(orderProductList);
		orderRepository.save(order);
	}
	
	public List<Order> getMyOrders() {
		return userService.getUserBySessionNoPassword().getOrderList();
	}
	
	public List<Order> getAllOrders() {
		
		List<Order> orderList = new ArrayList<Order>();
		orderRepository.findAll().forEach(order -> orderList.add(order));
		
		return orderList;
	}
	
	public boolean checkout() {
		List<OrderProduct> cartProductList = shoppingCartService.getOrderProductList();
		
		for(OrderProduct cartProduct: cartProductList) {
			
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
		
		List<OrderProduct> cartOrderProductList = shoppingCartService.getOrderProductList();
		
		for(OrderProduct cartOrderProduct: cartOrderProductList) {
			double price = cartOrderProduct.getProduct().getPrice();
			int quantity = cartOrderProduct.getQuantity();
			
			cost += price*quantity;
		}
		return cost;
		
	}
	
}
