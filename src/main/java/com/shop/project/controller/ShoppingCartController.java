package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.OrderProduct;
//import com.shop.project.service.OrderService;
import com.shop.project.service.ShoppingCartService;
//import com.shop.project.service.UserService;

@CrossOrigin
@RestController
public class ShoppingCartController {
	
	//@Autowired
	//private OrderService orderService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	//@Autowired
	//private UserService userService;

	
	
	@PostMapping("/addProductToCart")
	public void setProductQuantity(@RequestParam("productId") long productId, @RequestParam("quantity") int quantity) {
		shoppingCartService.setOrderProductByProductId(productId, quantity);
		
		shoppingCartService.getOrderProductList().forEach(ord -> System.out.print(ord.getProduct().getName()) );
		System.out.println("stfuuuuuuuuuuuuuuuu");
	}
	
	@PostMapping("/removeProductFromCart")
	public void removeProduct(@RequestParam("productId") long productId) {
		shoppingCartService.removeOrderProductByProductId(productId);
	}
	
	
	@GetMapping("/showShoppingCart")
	public List<OrderProduct> showShoppingCart() {
		
		return shoppingCartService.getOrderProductList();
	}
	/*
	@GetMapping("/makeOrder")
	public String confirmOrder(Model model) {
		if(userService.getSessionUserName()==null) {
			System.out.println("You have to login first");
			return "redirect:/login";
		}
		if(orderService.checkout()==false) {
			System.out.println("No product available");
			return "redirect:/makeOrder";
		}
		
		double orderCost = orderService.getOrderCost();
		double moneyAvailable = userService.getUserBySession().getMoney();

		model.addAttribute("cost", orderCost);
		model.addAttribute("money", moneyAvailable);
		
		return "ConfirmOrder";
		
	}
	
	@PostMapping("/makeOrder")
	public String makeOrder() {
		orderService.makeOrder();
		return "redirect:/showOrders";
	}*/
}
