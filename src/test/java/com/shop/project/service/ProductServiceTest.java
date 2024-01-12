package com.shop.project.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.shop.project.model.Product;
import com.shop.project.repository.ProductRepository;

public class ProductServiceTest {

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
		Optional<Product> optionalEmptyProduct4 = Optional.empty();
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product0);
		productList.add(product1);
		productList.add(product2);
		productList.add(product3);
		
		Mockito.when(productRepository.findById(0L)).thenReturn(optionalProduct0);
		Mockito.when(productRepository.findById(1L)).thenReturn(optionalProduct1);
		Mockito.when(productRepository.findById(2L)).thenReturn(optionalProduct2);
		Mockito.when(productRepository.findById(3L)).thenReturn(optionalProduct3);
		Mockito.when(productRepository.findById(4L)).thenReturn(optionalEmptyProduct4);
		
		Mockito.when(productRepository.findAll()).thenReturn(productList);
		
		Mockito.when(productRepository.save(Mockito.any(Product.class)))
		.thenAnswer(argument -> {
			return argument.getArgument(0);
		});
	}

	@Test
	@DisplayName("GetProductById() return product with given id")
	void GetProductById__Return_Product_With_Given_Id() {
		Product product1 = new Product(1, "product1", "description1", 1.00, 100);
		
		Product foundProduct = productService.getProductById(1);
		assertEquals(product1, foundProduct);
	}
	
	@Test
	@DisplayName("GetProductById() return null if there is no product with such id")
	void GetProductById__Return_Null_If_There_Is_No_Product_With_Such_Id() {
		Product foundProduct = productService.getProductById(4);
		
		assertEquals(null, foundProduct);
	}
	
	@Test
	@DisplayName("DecreaseProductQuantity() decrease available quantity of product with given id and returns updated product")
	void DecreaseProductQuantity__Decrease_Available_Quantity_Of_Product_With_Given_Id_And_Returns_Updated_Product() {
		Product product2 = new Product(2, "product2", "description2", 1.00, 100);
		
		int decreaseQuantityBy = 23;
		Product updatedProduct = productService.decreaseProductQuantity(2, decreaseQuantityBy);
		assertEquals(product2.getQuantityAvailable() - decreaseQuantityBy, updatedProduct.getQuantityAvailable());
		
		product2.setQuantityAvailable(product2.getQuantityAvailable() - decreaseQuantityBy);
		assertEquals(product2, updatedProduct);
	}
	
	@Test
	@DisplayName("DecreaseProductQuantity return null if there is no product with such id")
	void DecreaseProductQuantity__Return_Null_If_There_Is_No_Product_With_Such_Id() {
		assertEquals(null, productService.decreaseProductQuantity(4, 1));
	}

}
