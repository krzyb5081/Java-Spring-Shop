package com.shop.project.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {

	private List<OrderProduct> orderedProductsList = new ArrayList<OrderProduct>();
	
	
	public List<OrderProduct> getList(){
		return orderedProductsList;
	}
	
	public void setList(List<OrderProduct> orderedProductsList) {
		this.orderedProductsList = orderedProductsList;
	}
}
