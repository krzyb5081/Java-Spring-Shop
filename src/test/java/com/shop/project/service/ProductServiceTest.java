package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.model.Product;
import com.shop.project.repository.ProductRepository;

class ProductServiceTest {

	ProductService productService;
	
	ProductRepository productRepository = Mockito.mock(ProductRepository.class);
	
	@BeforeEach
	void setUp() throws Exception {
		productService = new ProductService(productRepository);
		
		Product product0 = new Product(0, "product0", "description0", 1.00, 100);
		Product product1 = new Product(1, "product1", "description1", 1.00, 100);
		Product product2 = new Product(2, "product2", "description2", 1.00, 100);
		Product product3 = new Product(3, "product3", "description3", 1.00, 100);
		Optional<Product> optionalProduct0 = Optional.of(product0);
		Optional<Product> optionalProduct1 = Optional.of(product1);
		Optional<Product> optionalProduct2 = Optional.of(product2);
		Optional<Product> optionalProduct3 = Optional.of(product3);
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product0);
		productList.add(product1);
		productList.add(product2);
		productList.add(product3);
		
		Mockito.when(productRepository.findById(0L)).thenReturn(optionalProduct0);
		Mockito.when(productRepository.findById(1L)).thenReturn(optionalProduct1);
		Mockito.when(productRepository.findById(2L)).thenReturn(optionalProduct2);
		Mockito.when(productRepository.findById(3L)).thenReturn(optionalProduct3);
		
		Mockito.when(productRepository.findAll()).thenReturn(productList);
		
		Mockito.when(productRepository.save(Mockito.any(Product.class)))
		.thenAnswer(argument -> {
			return argument.getArgument(0);
		});
	}

	@Test
	void testGetProductById() {
		Product product1 = new Product(1, "product1", "description1", 1.00, 100);
		
		assertEquals(product1, productService.getProductById(1));
		
	}


}
