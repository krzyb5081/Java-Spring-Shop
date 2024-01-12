package com.shop.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.shop.project.repository.OrderProductRepository;
import com.shop.project.repository.OrderRepository;

public class OrderServiceTest {

	private OrderService orderService;
	
	private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
	private final OrderProductRepository orderProductRepository = Mockito.mock(OrderProductRepository.class);
	private final ProductService productService = Mockito.mock(ProductService.class);
	private final ShoppingCartService shoppingCartService = Mockito.mock(ShoppingCartService.class);
	private final SessionService sessionService = Mockito.mock(SessionService.class);
	
	@BeforeEach
	void setUp() throws Exception{
		orderService = new OrderService(orderRepository,orderProductRepository,productService,shoppingCartService,sessionService);
		
	}
	
}
