package com.shop.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	public List<Order> findByUserId(long userId);
}