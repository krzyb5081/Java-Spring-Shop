package com.shop.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		if(quantity<1) {
			removeOrderProductByProductId(productId);
			return;
		}
		
		List<OrderProduct> orderProductList = shoppingCart.getOrderProductList();
		
		for(OrderProduct orderProduct : orderProductList) {
			if(orderProduct.getProduct().getId()==productId) {
				orderProductList.remove(orderProduct);
				break;
			}
		}
		
		OrderProduct orderProduct = new OrderProduct();
		
		Product product = productService.getProductById(productId);
		orderProduct.setProduct(product);
		orderProduct.setQuantity(quantity);
		
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
		return shoppingCart.getOrderProductList();
	}
	
	public Map<Long,Product> getProductMapForOrderProductList() {
		
		List<OrderProduct> cartOrderProductList = shoppingCart.getOrderProductList();
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		
		for(OrderProduct cartOrderProduct: cartOrderProductList) {
			
			long productId = cartOrderProduct.getProduct().getId();
			Product product = productService.getProductById(productId);
			
			productMap.put(productId, product);
		}
		return productMap;
		
	}
	
}
