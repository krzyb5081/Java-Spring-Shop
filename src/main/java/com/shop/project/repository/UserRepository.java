package com.shop.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.shop.project.model.User;

public interface UserRepository extends CrudRepository<User, Long> { }
