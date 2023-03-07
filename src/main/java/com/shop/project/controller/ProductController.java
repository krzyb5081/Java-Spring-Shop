package com.shop.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.Product;
import com.shop.project.service.ProductService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/addProduct")
	public void addProduct(@Valid Product product) {
		productService.addProduct(product);
	}
	
	@PostMapping("/changeProductProperties")
	public void changeProperties(@Valid Product product) {
		productService.changeProductProperties(product);
	}
	
	@GetMapping("/showProducts")
	public List<Product> showProducts() {
		return productService.getProductList();
	}
}
