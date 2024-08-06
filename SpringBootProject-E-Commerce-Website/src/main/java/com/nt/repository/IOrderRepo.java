package com.nt.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nt.model.Order;

public interface IOrderRepo extends CrudRepository<Order, Integer>{
	//Find orders by user id
	public List<Order> findByUserId(Integer uid);
}
