package com.nt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nt.model.WishList;

import jakarta.transaction.Transactional;

public interface IWishlistRepo extends CrudRepository<WishList, Integer> {
	//Get all wishlist products by user id
	public List<WishList> getWishlistByUserId(Integer uid);
	
	//Delete product from wishlist by user id & product id
	@Modifying
    @Transactional
    @Query("DELETE FROM WishList w WHERE w.userId = ?1 AND w.pid = ?2")
    void deleteByProductIdAndUserId(Integer userId, Integer pid);
	
}
