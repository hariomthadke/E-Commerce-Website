package com.nt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Cart;
import com.nt.model.Products;
import com.nt.repository.ICartRepo;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private ICartRepo repo;

	@Autowired
	private IProductsService productService;


	//Add product to cart by user id, product id & product quantity
	@Override
	public String saveToCart(Integer uid, Integer pid, Integer quantity) {
		//Check cart product is available in cart or not
		Optional<Cart> cartProduct=repo.findByUserIdAndProductId(uid, pid);

		if(cartProduct.isPresent()) {//Product is already available in cart
			//Get product
			Cart cart=cartProduct.get();
			//Increase quantity to by new quantity
			cart.setQuantity(cart.getQuantity()+quantity);
			//Store cart details in database
			repo.save(cart);

		}else {//Product is not available in cart
			//Get product by passing product id
			Products product=productService.findProductById(pid);
			//Create new Cart object and set details
			Cart cart=new Cart();
			cart.setQuantity(quantity);
			cart.setUserId(uid);
			cart.setProductDetails(product);
			//Store cart details in database
			repo.save(cart);
		}
		//return message
		return  "<strong>"+quantity+"</strong>"+" Products are added to Cart Successfully";
	}

	//Get all cart products by user id
	@Override
	public List<Cart> getAllCartProductsByActiveUserId(Integer id) {
		//Get cart products list by user id
		List<Cart> cartList=repo.findByUserId(id);
		//return cart list
		return cartList;
	}

	//Remove product from cart by cart id
	@Override
	public String removeCartByCartId(Integer cartId) {
        //Delete product from cart
		repo.deleteById(cartId);
        //return message
		return "Cart record is deleted successfully";
	}

	//Count total items in cart by user id
	@Override
	public Integer countTotalCartNoByUserId(Integer uid) {
		return repo.findByUserId(uid).size();
	}

	//Remove all products by user id
	@Override
	public String removeCartProductsByUserId(Integer userId) {
		//Delete all products from cart for received user id
		repo.deleteCartProductsByUserId(userId);
		return "Records deleted successfully";
	}

}
