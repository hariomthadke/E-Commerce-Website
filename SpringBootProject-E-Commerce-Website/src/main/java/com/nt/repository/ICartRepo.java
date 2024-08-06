package com.nt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nt.model.Cart;

import jakarta.transaction.Transactional;

public interface ICartRepo extends CrudRepository<Cart, Integer> {
	//Find user by user id
	public List<Cart> findByUserId(Integer uid);
	
	//Find cart item by user id & product id
	@Query("SELECT c FROM Cart c WHERE c.userId = ?1 AND c.productDetails.id = ?2")
    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);
	
    //Delete products from cart by user id
    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.userId = :userId")
    void deleteCartProductsByUserId(Integer userId);
	
	
	}

