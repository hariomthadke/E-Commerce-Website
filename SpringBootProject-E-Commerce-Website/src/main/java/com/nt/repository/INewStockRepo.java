package com.nt.repository;

import org.springframework.data.repository.CrudRepository;

import com.nt.model.NewStock;

public interface INewStockRepo extends CrudRepository<NewStock, Integer>{

}
