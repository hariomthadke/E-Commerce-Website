package com.nt.repository;

import org.springframework.data.repository.CrudRepository;

import com.nt.model.Category;

public interface ICategoryRepo extends CrudRepository<Category, Integer> {
	
}
