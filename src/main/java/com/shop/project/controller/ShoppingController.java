package com.shop.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.Product;
import com.shop.project.service.ProductService;

@CrossOrigin
@RestController
public class ShoppingController {
	
	@Autowired
	ProductService productService;

	@GetMapping("/shopping")
	public List<Product> showProducts(Model model) {
		
		List<Product> productList = productService.getProductList();
		
		model.addAttribute("productList", productList);
		return productList;
	}
	
	@PostMapping("/shopping")
	public List<Product> searchProducts(@RequestParam(name="searchText") String searchText, Model model) {
		List<Product> foundProductList = productService.searchProducts(searchText);
		model.addAttribute("productList", foundProductList);
		return foundProductList;
	}
}
