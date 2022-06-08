package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.OrderProduct;
import com.shop.project.service.OrderService;
import com.shop.project.service.ShoppingCartService;
import com.shop.project.service.UserService;

@CrossOrigin(origins = "http://localhost", allowCredentials = "true")
@RestController
public class ShoppingCartController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/addProductToCart")
	public void setProductQuantity(@RequestParam("productId") long productId, @RequestParam("quantity") int quantity) {
		shoppingCartService.setOrderProductByProductId(productId, quantity);
	}

	@PostMapping("/removeProductFromCart")
	public void removeProduct(@RequestParam("productId") long productId) {
		shoppingCartService.removeOrderProductByProductId(productId);
	}

	@GetMapping("/showShoppingCart")
	public List<OrderProduct> showShoppingCart() {
		return shoppingCartService.getOrderProductList();
	}
	
	@GetMapping("/getOrderCost")
	public Double getOrderCost() {
		return orderService.getOrderCost();
	}
	
	@GetMapping("/makeOrder")///////////////////////
	public String makeOrder() {
		if(userService.getUserBySession() == null) {
			System.out.println("You have to login first");
			return "You have to login first";
		}
		if(orderService.checkout()==false) {
			System.out.println("No product available");
			return "No product available";
		}
		
		orderService.makeOrder();
		return "Order made";
	}
}
