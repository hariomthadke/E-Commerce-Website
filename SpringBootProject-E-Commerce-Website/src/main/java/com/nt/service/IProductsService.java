package com.nt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nt.model.Category;
import com.nt.model.Products;

public interface IProductsService {
    //Store product image in local folder
	public boolean storeProductImage(MultipartFile file, Integer random);
	//Store product to database
	public boolean addProduct(Products product);
	//Get all products
	public List<Products> getAllProducts();
	//Delete product by product id
	public String deleteProductById(Integer id);
	//Find product by product id
	public Products findProductById(Integer id);
	//Update product
	public boolean updateProduct(Products product, MultipartFile file);
	//Get all products by category id
	public List<Products> getAllProductsByCategoryId(Integer id);
	//Get latest three products
	public List<Products> getLatestThreeProducts();
	//Get top 4 deals products
	public List<Products> getTopDealsFourProducts();
}
