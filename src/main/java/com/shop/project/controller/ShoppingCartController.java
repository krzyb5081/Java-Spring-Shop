package com.shop.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.project.service.OrderService;
import com.shop.project.service.ShoppingCartService;
import com.shop.project.service.UserService;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private UserService userService;

	
	
	@PostMapping("/addProductToCart")
	public String setProduct(@RequestParam("productId") long productId, @RequestParam("quantity") int quantity) {
		shoppingCartService.setOrderProductByProductId(productId, quantity);
		return"redirect:/show";
	}
	
	@PostMapping("/removeProductFromCart")
	public String removeProduct(@RequestParam("productId") long productId) {
		shoppingCartService.removeOrderProductByProductId(productId);
		return "redirect:/showShoppingCart";
	}
	
	
	@GetMapping("/showShoppingCart")
	public String showShoppingCart(Model model) {
		model.addAttribute("orderProductList", shoppingCartService.getOrderProductList());
		model.addAttribute("productMap", shoppingCartService.getCurrentProductMap());
		return "ShowCart";
	}
	
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
	}
}
