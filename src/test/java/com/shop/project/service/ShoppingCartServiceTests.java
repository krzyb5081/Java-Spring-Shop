package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.shop.project.dto.ShoppingCart;
import com.shop.project.model.OrderPart;
import com.shop.project.model.Product;

public class ShoppingCartServiceTests {
	
	private ShoppingCartService shoppingCartService;
	
	private ShoppingCart shoppingCart = Mockito.mock(ShoppingCart.class);
	private ProductService productService = Mockito.mock(ProductService.class);
	
	@BeforeEach
	void initShoppingCartService() throws Exception{
		
		shoppingCartService = new ShoppingCartService(shoppingCart, productService);
		
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
		
		Mockito.when(productService.getProductById(0)).thenReturn(product0);
		Mockito.when(productService.getProductById(1)).thenReturn(product1);
		Mockito.when(productService.getProductById(2)).thenReturn(product2);
		Mockito.when(productService.getProductById(3)).thenReturn(product3);
		
	}
	
	@Test
	@DisplayName("removeOrderPartByProductId() removes OrderPart by OrderPart.product.id")
	void removeOrderPartByProductId__removes_OrderPart__by__OrderPart_product_id() {
		List<OrderPart> orderPartList = new ArrayList<OrderPart>(shoppingCart.getOrderPartList());
		List<OrderPart> newOrderPartList = shoppingCartService.removeOrderPartByProductId(1);
		
		assertNotEquals(orderPartList.size(), newOrderPartList.size());
		assertTrue(newOrderPartList.contains(orderPartList.get(0)));
		assertFalse(newOrderPartList.contains(orderPartList.get(1)));
		
	}
	
	@Test
	@DisplayName("changeOrderPartQuantityByProductId() in ShoppingCart.orderPartList changes OrderPart.quantity by OrderPart.product.id")
	void changeOrderPartQuantityByProductId__in_ShoppingCart_orderPartList__changes_OrderPart_quantity__by__OrderPart_product_id() {
		List<OrderPart> orderPartList = new ArrayList<OrderPart>(shoppingCart.getOrderPartList());
		assertEquals(5, orderPartList.get(2).getQuantity());
		
		List<OrderPart> newOrderPartList = shoppingCartService.changeOrderPartQuantityByProductId(2, 3);
		
		assertEquals(orderPartList.size(), newOrderPartList.size());
		assertEquals(orderPartList.get(2).getProduct().getId(), newOrderPartList.get(3).getProduct().getId());
		assertEquals(3, newOrderPartList.get(3).getQuantity());
	}
	
	@Test
	@DisplayName("changeOrderPartQuantityByProductId() removes OrderPart from ShoppingCart.orderPartList if OrderPart.quantity is set to 0")
	void changeOrderPartQuantityByProductId__removes__OrderPart__from__ShoppingCart_orderPartList__if__OrderPart_quantity__is__set__to__0() {
		List<OrderPart> orderPartList = new ArrayList<OrderPart>(shoppingCart.getOrderPartList());
		List<OrderPart> newOrderPartList = shoppingCartService.changeOrderPartQuantityByProductId(3, 0);
		
		assertNotEquals(orderPartList.size(), newOrderPartList.size());
		assertTrue(newOrderPartList.contains(orderPartList.get(0)));
		assertFalse(newOrderPartList.contains(orderPartList.get(3)));
	}
	
	@Test
	@DisplayName("getOrderPartList() returns same list as ShoppingCart.getOrderPartList()")
	void getOrderPartList__returns__same__list__as__ShoppingCart_getOrderPartList() {
		List<OrderPart> orderPartList = new ArrayList<OrderPart>(shoppingCart.getOrderPartList());
		List<OrderPart> newOrderPartList = shoppingCartService.getOrderPartList();
		
		assertEquals(orderPartList, newOrderPartList);
	}
	
	@Test
	@DisplayName("clearShoppingCart() returns empty List<OrderPart>")
	void clearShoppingCart() {
		List<OrderPart> emptyShoppingCart = shoppingCartService.clearShoppingCart();
		assertTrue(emptyShoppingCart.isEmpty());
	}
}
