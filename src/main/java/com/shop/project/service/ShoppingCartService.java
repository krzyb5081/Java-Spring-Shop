package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.shop.project.dto.ShoppingCart;
import com.shop.project.model.OrderPart;
import com.shop.project.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
	
	private final ShoppingCart shoppingCart;
	private final ProductService productService;
	
	
	public List<OrderPart> removeOrderPartByProductId(long productId) {
		
		List<OrderPart> orderPartList = shoppingCart.getOrderProductList();
		
		for(OrderPart orderPart: orderPartList) {
			if(orderPart.getProduct().getId()==productId) {
				orderPartList.remove(orderPart);
				break;
			}
		}
		shoppingCart.setOrderProductList(orderPartList);
		
		return orderPartList;
		
	}
	
	public List<OrderPart> setOrderPartByProductId(long productId, int quantity) {
		
		removeOrderPartByProductId(productId);
		if(quantity < 1) return new ArrayList<OrderPart>();
		
		Product product = productService.getProductById(productId);
		
		OrderPart orderPart = new OrderPart();
		orderPart.setProduct(product);
		orderPart.setQuantity(quantity);
		
		List<OrderPart> orderPartList = shoppingCart.getOrderProductList();
		orderPartList.add(orderPart);
		shoppingCart.setOrderProductList(orderPartList);
		
		return orderPartList;
		
	}
	
	public List<OrderPart> getOrderPartList() {
		System.out.println("ShoppingCartService >> getOrderPartList() >> isEmpty(): "+shoppingCart.getOrderProductList().isEmpty());
		return shoppingCart.getOrderProductList();
	}
	
	public List<OrderPart> clearShoppingCart() {
		List<OrderPart> orderPartList = new ArrayList<OrderPart>();
		shoppingCart.setOrderProductList(orderPartList);
		return orderPartList;
	}
	
}
