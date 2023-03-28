package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.shop.project.model.Product;
import com.shop.project.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	public Product getProductById(long productId) {
		try {
			return productRepository.findById(productId).get();
		}
		catch(NoSuchElementException exception) {
			return null;
		}
			
	}
	
	public Product decreaseProductQuantity(long productId, int decreaseBy) {
		Product product = getProductById(productId);
		
		if(product != null) {
			product.setQuantityAvailable(product.getQuantityAvailable()-decreaseBy);
			product = productRepository.save(product);	
		}
		
		return product;
	}
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public void changeProductProperties(Product changedProduct) {
		productRepository.save(changedProduct);
	}
	
	public List<Product> getProductList(){
		List<Product> productList = new ArrayList<Product>();
		
		productRepository.findAll().forEach(product->{
			productList.add(product);
		});
		return productList;
	}
	
	public List<Product> searchProducts(String searchText) {
		
		List<Product> foundProductsList = productRepository.findBySearchText(searchText);
		
		return foundProductsList;
	}
}
