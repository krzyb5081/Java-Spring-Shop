package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.dto.ShoppingCart;
import com.shop.project.model.OrderPart;
import com.shop.project.model.Product;
import com.shop.project.repository.OrderPartRepository;
import com.shop.project.repository.OrderRepository;
import com.shop.project.repository.UserRepository;

public class OrderServiceTest {

	private OrderService orderService;
	
	private final OrderPartRepository orderPartRepository = Mockito.mock(OrderPartRepository.class);
	private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
	private final UserRepository userRepository = Mockito.mock(UserRepository.class);
	private final ProductService productService = Mockito.mock(ProductService.class);
	private final ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
	private final ShoppingCartService shoppingCartService = Mockito.mock(ShoppingCartService.class);
	private final SessionService sessionService = Mockito.mock(SessionService.class);
	
	@BeforeEach
	void setUp() throws Exception{
		orderService = new OrderService(orderPartRepository,orderRepository,userRepository,productService,shoppingCartService,sessionService);
		
		Product product0 = new Product(0, "name0", "description0", 1.0, 10);
		Product product1 = new Product(1, "name1", "description1", 1.0, 10);
		Product product2 = new Product(2, "name2", "description2", 1.0, 10);
		Product product3 = new Product(3, "name3", "description3", 1.0, 10);
		
		OrderPart orderPart0 = new OrderPart(0, 5, null, product0);
		OrderPart orderPart1 = new OrderPart(1, 5, null, product1);
		OrderPart orderPart2 = new OrderPart(2, 5, null, product2);
		OrderPart orderPart3 = new OrderPart(3, 5, null, product3);
		
		List<OrderPart> orderPartList = new ArrayList<OrderPart>();
		orderPartList.add(orderPart0);
		orderPartList.add(orderPart1);
		orderPartList.add(orderPart2);
		orderPartList.add(orderPart3);
		
		Mockito.when(shoppingCart.getOrderPartList()).thenReturn(orderPartList);
		
	}
	
	@Test
	@DisplayName("makeOrder() works")
	void makeOrderTest() {
		
	}
	
	@Test
	@DisplayName("getMyOrders() works")
	void getMyOrdersTest() {
		
	}
	
	@Test
	@DisplayName("getAllOrders() works")
	void getAllOrdersTest() {
		
	}
	
	@Test
	@DisplayName("checkAvailability() works")
	void checkAvailabilityTest() {
		
	}
	
	@Test
	@DisplayName("getOrderCost() works")
	void getOrderCostTest() {
		
	}
}
