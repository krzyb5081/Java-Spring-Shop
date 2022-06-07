package com.shop.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.OrderProduct;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {

	public List<OrderProduct> findByOrderId(long orderId);
}