package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.project.model.Product;
import com.shop.project.service.ProductService;

@Controller
public class ShoppingController {
	
	@Autowired
	ProductService productService;

	@GetMapping("/shopping")
	public String showProducts(Model model) {
		
		List<Product> productList = productService.getProductList();
		
		model.addAttribute("productList", productList);
		return "ShowProducts";
	}
	
	@PostMapping("/shopping")
	public String searchProducts(@RequestParam(name="searchText") String searchText, Model model) {
		List<Product> foundProductList = productService.searchProducts(searchText);
		model.addAttribute("productList", foundProductList);
		return "ShowProducts";
	}
}
