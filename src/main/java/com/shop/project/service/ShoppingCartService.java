package com.shop.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.project.model.OrderProduct;
import com.shop.project.model.Product;
import com.shop.project.model.ShoppingCart;

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
		
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setProduct(product);
		orderProduct.setQuantity(quantity);
		
		List<OrderProduct> orderProductList = shoppingCart.getOrderProductList();
		orderProductList.add(orderProduct);
		shoppingCart.setOrderProductList(orderProductList);
		
	}
	
	public void removeOrderProductByProductId(long productId) {
		
		List<OrderProduct> orderProductList = shoppingCart.getOrderProductList();
		
		for(OrderProduct orderProduct: orderProductList) {
			if(orderProduct.getProduct().getId()==productId) {
				orderProductList.remove(orderProduct);
				break;
			}
		}
		shoppingCart.setOrderProductList(orderProductList);
		
	}
	
	public List<OrderProduct> getOrderProductList() {
		System.out.println("ShoppingCartService >> getOrderProductList() >> isEmpty(): "+shoppingCart.getOrderProductList().isEmpty());
		return shoppingCart.getOrderProductList();
	}
	
	public void clearShoppingCart() {
		shoppingCart.setOrderProductList(new ArrayList<OrderProduct>());
		return;
	}
	
}
