package com.shop.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shop.project.model.OrderPart;

public interface OrderPartRepository extends CrudRepository<OrderPart, Long> {

	public List<OrderPart> findByOrderId(long orderId);
}