package com.shop.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.project.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	@Query(value = "select p from Product p where p.name like %:searchText%")
	List<Product> findBySearchText(@Param("searchText") String searchText);
}