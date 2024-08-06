package com.nt.service;

import java.util.HashMap;
import java.util.List;

import com.nt.model.Order;

public interface IOrderService {
	//Place order (single product)
	public HashMap<String, String> placeOrder(Integer userId, Integer productId, Integer quantity, String paymentType);
	//Place all cart orders
	public HashMap<String, String> placeAllCartOrders(Integer userId, String paymentMode);
	//Get all orders by user id
	public List<Order> getAllOrdersByUserId(Integer userId);
	//Get all orders
	public List<Order> getAllOrders();
	//Get all order addresses
	public HashMap<Integer, String> getAllOrdersAddress(List<Order> orderList);
	//Update order status
	public String updateOrderStatusByOrderId(String orderStatus, String orderId);
	

}
