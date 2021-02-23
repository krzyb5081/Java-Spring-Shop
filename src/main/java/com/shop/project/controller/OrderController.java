package com.shop.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.project.service.OrderService;
import com.shop.project.service.UserService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	@GetMapping("/showOrders")
	public String showOrders(Model model) {
		if(userService.getSessionUserName()==null) {
			return "redirect:/login";
		}
		
		if(userService.getUserBySession().getType().equals("seller")) {
			model.addAttribute("orderEntrySet", orderService.getAllOrders().entrySet());
			model.addAttribute("productMap", orderService.getProductMap());
			model.addAttribute("userMap", orderService.getUserMap());
		}
		else {
			model.addAttribute("orderEntrySet", orderService.getMyOrders().entrySet());
			model.addAttribute("productMap", orderService.getProductMap());
			model.addAttribute("userMap", orderService.getUserMap());
		}
		return "showOrders";
	}
}
