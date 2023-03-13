package com.shop.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.OrderPart;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderPart, Long> {

	public List<OrderPart> findByOrderId(long orderId);
}