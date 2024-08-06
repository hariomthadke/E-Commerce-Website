package com.nt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Cart;
import com.nt.model.Order;
import com.nt.model.Products;
import com.nt.model.Users;
import com.nt.repository.IOrderRepo;

@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	private IOrderRepo repo;

	@Autowired
	private IProductsService productService;

	@Autowired
	private ICartService cartService;

	@Autowired
	private IUsersService userService;

	//Place order (single product)
	@Override
	public HashMap<String, String> placeOrder(Integer userId, Integer productId, Integer quantity, String paymentType) {
		//Get product by product id
		Products product=productService.findProductById(productId);

		//Get available product quantity
		Integer availableProductQuantity=product.getQuantity();
		//Declare HashMap object for storing status & message
		HashMap<String, String> hashMap=new HashMap<>();

		if(availableProductQuantity<quantity) {//Available product quantity is less than received quantity
			if(availableProductQuantity==0) {//If available product quantity is 0
				//Store status & message in HashMap
				hashMap.put("status", "false");
				hashMap.put("message", "Product Out of Stock");
				//Return hashMap
				return hashMap;
			}
			else {//If available product quantity is more than 0
				//Store status & message in HashMap
				hashMap.put("status", "false");
				hashMap.put("message", "Product Out of Stock :: Only "+product.getQuantity()+" is Available");
				//Return hashMap
				return hashMap;
			}
		}
		else if(product.getQuantity()>=quantity) {//Available product quantity is more than received quantity
			//Declare order status as confirmed
			String orderStatus="Confirmed";

			//Create Order object and store order details
			Order order=new Order();
			order.setUserId(userId);
			order.setProductDetails(product);
			order.setQuantity(quantity);
			order.setPaymentType(paymentType);
			order.setStatus(orderStatus);
			order.setPrice(product.getPriceAfterDiscount());
			//Store order details to database
			repo.save(order);

			//Reduce actual product quantity by ordered product quantity
			product.setQuantity(product.getQuantity()-quantity);
			//Store updated product quantiy to database
			productService.addProduct(product);
			//Store status & message into hashMap
			hashMap.put("status", "true");
			hashMap.put("message", "Order Placed Successfully");


		}
		//Return hashMap
		return hashMap;
	}

	//Place all cart orders
	@Override
	public HashMap<String, String> placeAllCartOrders(Integer userId, String paymentMode) {
		// Retrieve all cart products for the active user
		List<Cart> cartList = cartService.getAllCartProductsByActiveUserId(userId);
		boolean allProductsAvailable = true;
		HashMap<String, String> hashMap = new HashMap<>();

		//Declare StringBuilder
		StringBuilder outOfStockMessage = new StringBuilder("<h4>The Following Products is/are out of Stock::</h4><hr>");

		// Check if any products in the cart are out of stock
		for (Cart cart : cartList) {
			//Get product detials from cart object
			Products product = cart.getProductDetails();

			if (cart.getQuantity() > product.getQuantity()) {//If required quantiy if more than available quantity
				//Frame out of stock message
				outOfStockMessage.append("<strong>").append(product.getName()).append("</strong>")
				.append(" is Out of Stock, Available quantity is: ")
				.append("<strong>").append(product.getQuantity()).append("</strong><br>");

				//set allProductsAvailable false
				allProductsAvailable = false;
			}
		}

		if (allProductsAvailable) {//All products are available in stock
			//Iterate over the cart list and place orders
			for (Cart cart : cartList) {
				Products product = cart.getProductDetails();

				// Create new order and save order details
				Order order = new Order();
				order.setUserId(userId);
				order.setProductDetails(product);
				order.setQuantity(cart.getQuantity());
				order.setPaymentType(paymentMode);
				order.setStatus("Confirmed");
				order.setPrice(product.getPriceAfterDiscount());
				//Store order details in database
				repo.save(order);

				// Update product quantity
				product.setQuantity(product.getQuantity() - cart.getQuantity());
				//Store updated product quantiy to database
				productService.addProduct(product);
			}
			//Store message to hashMap
			hashMap.put("status", "true");
			hashMap.put("message", "All Orders are Placed Successfully");

			// Remove all cart products for the user
			cartService.removeCartProductsByUserId(userId);
			
		} else {// Some or All products are out of stock
			//Store status & message in hashMap
			hashMap.put("status", "false");
			hashMap.put("message", outOfStockMessage.toString());
		}

		return hashMap;
	}


	//Get all orders by user id
	@Override
	public List<Order> getAllOrdersByUserId(Integer userId) {
		return  repo.findByUserId(userId);	
	}

	//Get all orders
	@Override
	public List<Order> getAllOrders() {
		return (List<Order>) repo.findAll();
	}

	//Get all order addresses
	@Override
	public HashMap<Integer, String> getAllOrdersAddress(List<Order> orderList) {
		HashMap<Integer, String> hashMap = new HashMap<>();

		for (Order order : orderList) {
			//Retrieve user id from order
			Integer userId = order.getUserId();
			//Retrieve the user details by user ID
			Users user = userService.getUserById(userId);
			if (user != null) {
				//Construct the user's address information
				String userAddrs = user.getName() + "<br>" + user.getMobileNo() + "<br>" + user.getAddress() + "<br>" + user.getCity() + " : " + user.getPincode();
				//Put the user's address information in the hashmap with user ID as the key
				hashMap.put(userId, userAddrs);
			}
		}

		return hashMap;
	}


	//Update order status
	@Override
	public String updateOrderStatusByOrderId(String orderStatus, String orderId) {
		//If order status is Deliverd then delete that order
		if(orderStatus.equals("Delivered")) {
			repo.deleteById(Integer.parseInt(orderId));
		}else {
		//Get Order object by order id
		Optional<Order> op=repo.findById(Integer.parseInt(orderId));

		if(op.isPresent()) {//If order is available
			//Get Order object from Optional
			Order order=op.get();
			//Set new status for the order
			order.setStatus(orderStatus);
			//Save the updated order back to the database
			repo.save(order);
		}
		}
		return "Order Updated Successfully";
	}
}
