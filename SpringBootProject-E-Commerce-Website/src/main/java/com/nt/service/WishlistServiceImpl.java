package com.nt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Products;
import com.nt.model.WishList;
import com.nt.repository.IWishlistRepo;

@Service
public class WishlistServiceImpl implements IWishlistService{

	@Autowired
	private IWishlistRepo repo;

	@Autowired
	private IProductsService productService;

	
	//Get all wishlist  by user id
	@Override
	public List<WishList> getAllWishlistByActiveUserId(Integer userId) {
		return repo.getWishlistByUserId(userId);
	}
	

	//Add product to wishlist by user id & product id
	@Override
	public String addWishlistProductByUserId(Integer userId, Integer productId) {
		//Create WishList object and set user id & product Id
		WishList wishlist=new WishList();
		wishlist.setUserId(userId);
		wishlist.setPid(productId);

		//Store Wishlist details to database
		repo.save(wishlist);
		//return message
		return "Wishlist record is saved successfully";
	}

	
	//Remove product from wishlist by user id & product id
	@Override
	public String removeWishlistProductByUserId(Integer userId, Integer productId) {
		repo.deleteByProductIdAndUserId(userId, productId);
		//return message
		return "Wishlist record is deleted successfully";
	}
	
	
	//Get all wishlist products by user id
	@Override
	public List<Products> getAllWishlistProductsByUserId(Integer uid) {
		//Get all Wishlist products by user id
		List<WishList> wishList=this.getAllWishlistByActiveUserId(uid);
		//Create list to store all products for user
		List<Products> productList=new ArrayList<>();

		//Store all products to product list
		for(WishList wl: wishList) {
			productList.add(productService.findProductById(wl.getPid()));
		}

		//return product list
		return productList;
	}

}
