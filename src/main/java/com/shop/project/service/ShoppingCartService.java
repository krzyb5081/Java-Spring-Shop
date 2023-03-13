package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.project.dto.ShoppingCart;
import com.shop.project.model.OrderPart;
import com.shop.project.model.Product;

@Service
public class ShoppingCartService {
	
	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private ProductService productService;
	
	public void setOrderProductByProductId(long productId, int quantity) {
		
		removeOrderProductByProductId(productId);
		if(quantity < 1) return;
		
		Product product = productService.getProductById(productId);
		
		OrderPart orderProduct = new OrderPart();
		orderProduct.setProduct(product);
		orderProduct.setQuantity(quantity);
		
		List<OrderPart> orderProductList = shoppingCart.getOrderProductList();
		orderProductList.add(orderProduct);
		shoppingCart.setOrderProductList(orderProductList);
		
	}
	
	public void removeOrderProductByProductId(long productId) {
		
		List<OrderPart> orderProductList = shoppingCart.getOrderProductList();
		
		for(OrderPart orderProduct: orderProductList) {
			if(orderProduct.getProduct().getId()==productId) {
				orderProductList.remove(orderProduct);
				break;
			}
		}
		shoppingCart.setOrderProductList(orderProductList);
		
	}
	
	public List<OrderPart> getOrderProductList() {
		System.out.println("ShoppingCartService >> getOrderProductList() >> isEmpty(): "+shoppingCart.getOrderProductList().isEmpty());
		return shoppingCart.getOrderProductList();
	}
	
	public void clearShoppingCart() {
		shoppingCart.setOrderProductList(new ArrayList<OrderPart>());
		return;
	}
	
}
