package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.model.Order;
import com.shop.project.model.OrderPart;
import com.shop.project.model.Product;
import com.shop.project.model.User;
import com.shop.project.repository.OrderPartRepository;
import com.shop.project.repository.OrderRepository;
import com.shop.project.repository.UserRepository;

public class OrderServiceTest {

	private OrderService orderService;
	
	private final OrderPartRepository orderPartRepository = Mockito.mock(OrderPartRepository.class);
	private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
	private final UserRepository userRepository = Mockito.mock(UserRepository.class);
	private final ProductService productService = Mockito.mock(ProductService.class);
	private final ShoppingCartService shoppingCartService = Mockito.mock(ShoppingCartService.class);
	private final SessionService sessionService = Mockito.mock(SessionService.class);
	
	
	private Product product0;
	private Product product1;
	private Product product2;
	private Product product3;
	
	private OrderPart orderPart0;
	private OrderPart orderPart1;
	private OrderPart orderPart2;
	private OrderPart orderPart3;
	
	private List<OrderPart> orderPartList;
	
	private User user0;
	private User user1;
	private User user2;
	private User user3;
	
	
	@BeforeEach
	void setUp() throws Exception{
		
		orderService = new OrderService(orderPartRepository,orderRepository,userRepository,productService,shoppingCartService,sessionService);
		
		product0 = new Product(0, "name0", "description0", 1.0, 10);
		product1 = new Product(1, "name1", "description1", 1.0, 10);
		product2 = new Product(2, "name2", "description2", 1.0, 10);
		product3 = new Product(3, "name3", "description3", 1.0, 10);
		
		orderPart0 = new OrderPart(0, 3, null, product0);
		orderPart1 = new OrderPart(1, 2, null, product1);
		orderPart2 = new OrderPart(2, 1, null, product2);
		orderPart3 = new OrderPart(3, 0, null, product3);
		
		orderPartList = new ArrayList<OrderPart>();
		
		orderPartList.add(orderPart0);
		orderPartList.add(orderPart1);
		orderPartList.add(orderPart2);
		orderPartList.add(orderPart3);
		
		user0 = new User(0, "user0", "password0", "user", 150, null);
		user1 = new User(1, "user1", "password1", "user", 151, null);
		user2 = new User(2, "user2", "password2", "user", 152, null);
		user3 = new User(3, "user3", "password3", "user", 153, null);
		
		
		Mockito.when(shoppingCartService.getOrderPartList()).thenReturn(orderPartList);
		Mockito.when(sessionService.getUserFromSession()).thenReturn(user0);
	}
	
	@Test
	@DisplayName("makeOrder() check if user is logged in")
	void makeOrder_Check_If_User_Is_Logged_In() {
		Order resultOrder;
		resultOrder = orderService.makeOrder().values().iterator().next();
		
		assertTrue(resultOrder.getUser() == user0);
		assertTrue(resultOrder.getUser().getUserName() == "user0");
		
		Mockito.when(sessionService.getUserFromSession()).thenReturn(null);
		resultOrder = orderService.makeOrder().values().iterator().next();
		
		assertTrue(resultOrder.getUser() == null);
		
	}
	
	@Test
	@DisplayName("makeOrder() check if cart is empty")
	void makeOrder_Check_If_Cart_Is_Empty() {
		Order resultOrder;
		resultOrder  = orderService.makeOrder().values().iterator().next();
		
		assertTrue(resultOrder.getOrderPartList() != null);
		
	}
	
	@Test
	@DisplayName("makeOrder() bind newly created Order object to each OrderProduct object in the list")
	void makeOrder_Bind_New_Order_To_Each_OrderProduct() {
		Order resultOrder;
		resultOrder  = orderService.makeOrder().values().iterator().next();
		
		assertTrue(resultOrder.getOrderPartList().get(0).getQuantity() == orderPartList.get(0).getQuantity());
		assertTrue(resultOrder.getOrderPartList().get(0).getOrder() != null);
		assertTrue(resultOrder.getOrderPartList().get(0).getOrder().getOrderPartList() == orderPartList);
		
	}
	
	@Test
	@DisplayName("makeOrder() works only if products are available")
	void makeOrder_Check_Products_Availability() {
		List<OrderPart> orderPartListWrongQuantity = new ArrayList<OrderPart>();
		orderPartListWrongQuantity.add(new OrderPart(0, 7777, null, product0));
		
		Mockito.when(shoppingCartService.getOrderPartList()).thenReturn(orderPartListWrongQuantity);
		assertTrue(orderService.makeOrder().containsKey(false));
		
		Mockito.when(shoppingCartService.getOrderPartList()).thenReturn(this.orderPartList);
		assertTrue(orderService.makeOrder().containsKey(true));
		
	}
	
	@Test
	@DisplayName("makeOrder() set everything right")
	void makeOrder_Set_Everything_Right() {
		assertTrue(orderService.makeOrder().containsKey(true));
		
	}
	
	@Test
	@DisplayName("getMyOrders() return only orders belonging to logged in user")
	void getMyOrders_Return_Only_Orders_Belonging_To_User() {
		
	}
	
	@Test
	@DisplayName("getAllOrders() return every order")
	void getAllOrders_Return_Every_Order() {
		
	}
	
	@Test
	@DisplayName("checkAvailability() return true only when every every product is available with expected quantity")
	void checkAvailability_Return_True_When_Everything_Is_Available() {
		
	}
	
	@Test
	@DisplayName("getOrderCost() calculate order cost")
	void getOrderCost_Calculate_Order_Cost() {
		
	}
}
