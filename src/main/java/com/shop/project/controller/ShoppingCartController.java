package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.OrderProduct;
import com.shop.project.service.ShoppingCartService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;

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
	
}
