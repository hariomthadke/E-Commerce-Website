package com.nt.service;

import java.util.List;

import com.nt.model.Products;
import com.nt.model.WishList;

public interface IWishlistService {
	//Get all wishlist  by user id
	public List<WishList> getAllWishlistByActiveUserId(Integer id);
	//Add product to wishlist by user id & product id
	public String addWishlistProductByUserId(Integer uid, Integer pid);
	//Remove product from wishlist by user id & product id
	public String removeWishlistProductByUserId(Integer uid, Integer pid);
	//Get all wishlist products by user id
	public List<Products> getAllWishlistProductsByUserId(Integer uid);
}
