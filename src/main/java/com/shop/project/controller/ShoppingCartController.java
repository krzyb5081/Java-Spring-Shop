package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.OrderPart;
import com.shop.project.service.ShoppingCartService;

@RestController
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping("/addProductToCart")
	public void setProductQuantity(@RequestParam("productId") long productId, @RequestParam("quantity") int quantity) {
		shoppingCartService.changeOrderPartQuantityByProductId(productId, quantity);
	}

	@PostMapping("/removeProductFromCart")
	public void removeProduct(@RequestParam("productId") long productId) {
		shoppingCartService.removeOrderPartByProductId(productId);
	}

	@GetMapping("/showShoppingCart")
	public List<OrderPart> showShoppingCart() {
		return shoppingCartService.getOrderPartList();
	}
	
}
