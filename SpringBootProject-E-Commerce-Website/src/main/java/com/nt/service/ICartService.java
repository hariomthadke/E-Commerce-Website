package com.nt.service;

import java.util.List;

import com.nt.model.Cart;

public interface ICartService {
	//Add product to cart by user id, product id & product quantity
    public String saveToCart(Integer uid, Integer pid, Integer quantity);
    //Get all cart products by user id
    public List<Cart> getAllCartProductsByActiveUserId(Integer uid);
    //Remove product from cart by cart id
    public String removeCartByCartId(Integer cartId);
    //Count total items in cart by user id
    public Integer countTotalCartNoByUserId(Integer uid);
    //Remove all products by user id
    public String removeCartProductsByUserId(Integer userId);
    
}
