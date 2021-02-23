package com.shop.project.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.project.model.Order;
import com.shop.project.model.OrderProduct;
import com.shop.project.model.Product;
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
		
		List<OrderProduct> orderProduct = shoppingCartService.getOrderProductList();
		
		Order order = new Order();
		order.setUserId(userService.getSessionUserId());
		order.setStatus("paid");
		
		double cost = getOrderCost();
		userService.payForOrder(cost);
		
		Order savedOrder = orderRepository.save(order);
		orderProduct.forEach(ordProduct -> ordProduct.setOrderId(savedOrder.getId()));
		orderProduct.forEach(ordProduct -> {
			long productId = ordProduct.getProductId();
			productService.decreaseProductQuantity(productId, ordProduct.getQuantity());
		});
		orderProductRepository.saveAll(orderProduct);
		
	}
	
	public Map<Order,List<OrderProduct>> getMyOrders() {
		
		Map<Order,List<OrderProduct>> orderMap = new HashMap<Order,List<OrderProduct>>();
		
		List<Order> orderList = orderRepository.findByUserId(userService.getSessionUserId());
		orderList.forEach(order -> {
			List<OrderProduct> orderProductList = orderProductRepository.findByOrderId(order.getId());
			orderMap.put(order, orderProductList);
		});
		
		return Collections.unmodifiableMap(orderMap);
	}
	
	public Map<Order,List<OrderProduct>> getAllOrders() {
		
		Map<Order,List<OrderProduct>> orderMap = new HashMap<Order,List<OrderProduct>>();
		
		List<Order> orderList = orderRepository.getAllToList();
		orderList.forEach(order -> {
			List<OrderProduct> orderProductList = orderProductRepository.findByOrderId(order.getId());
			orderMap.put(order, orderProductList);
		});
		
		return Collections.unmodifiableMap(orderMap);
	}
	
	public boolean checkout() {
		List<OrderProduct> shoppingCartProducts = shoppingCartService.getOrderProductList();
		
		for(OrderProduct productsFromCart: shoppingCartProducts) {
			
			long productId = productsFromCart.getProductId();
			int quantity = productsFromCart.getQuantity();
			
			int quantityAvailable = productService.getProductById(productId).getQuantityAvailable();
			
			if(quantityAvailable < quantity) {
				return false;
			}
		}
		
		return true;
	}
	
	public double getOrderCost() {
		double cost = 0;
		
		List<OrderProduct> shoppingCartProducts = shoppingCartService.getOrderProductList();
		
		for(OrderProduct productsFromCart: shoppingCartProducts) {
			double price = productService.getProductById(productsFromCart.getProductId()).getPrice();
			int quantity = productsFromCart.getQuantity();
			
			cost += price*quantity;
		}
		return cost;
		
	}
	
	public Map<Long,Product> getProductMap() {
		
		return productService.getProductMap();
	}
	
	public Map<Long,User> getUserMap() {
		
		return userService.getUserMap();
	}
	
}
