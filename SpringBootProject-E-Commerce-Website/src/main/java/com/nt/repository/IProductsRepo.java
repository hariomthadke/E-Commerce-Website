package com.nt.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nt.model.Products;

public interface IProductsRepo extends CrudRepository<Products, Integer>{
	//Find products by category 
	public List<Products> findByCategoryType(Integer cid);
	
	//Find latest 3 products
	@Query("SELECT p FROM Products p ORDER BY p.id DESC")
    List<Products> findTop3LatestProducts(Pageable pageable);
	
	//Find top deals products
	@Query("SELECT  p FROM Products p ORDER BY p.discount DESC")
	List<Products> findTop4DealsProducts(Pageable pageable);
}
