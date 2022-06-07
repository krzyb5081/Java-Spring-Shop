package com.shop.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> { }
