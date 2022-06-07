package com.shop.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.project.model.Product;
import com.shop.project.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public Product getProductById(long productId) {
		return productRepository.findById(productId).get();
	}
	
	public void decreaseProductQuantity(long productId, int decreaseBy) {
		Product product = getProductById(productId);
		product.setQuantityAvailable(product.getQuantityAvailable()-decreaseBy);
		productRepository.save(product);
	}
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public void changeProductProperties(Product changedProduct) {
		productRepository.save(changedProduct);
	}
	
	public Map<Long,Product> getProductMap(){
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		
		productRepository.findAll().forEach(product->{
			productMap.put(product.getId(), product);
		});
		return productMap;
	}
	
	public List<Product> getProductList(){
		List<Product> productList = new ArrayList<Product>();
		
		productRepository.findAll().forEach(product->{
			productList.add(product);
		});
		return productList;
	}
	
	public List<Product> searchProducts(String searchText) {
		
		List<Product> productFoundList = productRepository.findBySearchText(searchText);
		
		return productFoundList;
	}
}
