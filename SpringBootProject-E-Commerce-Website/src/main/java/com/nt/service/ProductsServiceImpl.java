package com.nt.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nt.model.Products;
import com.nt.repository.IProductsRepo;

@Service
public class ProductsServiceImpl implements IProductsService {

	@Autowired
	private IProductsRepo repo;

	// Store the product image in a local folder
	@Override
	public boolean storeProductImage(MultipartFile file, Integer randomNumber) {
	    try {
	        // Get the directory where the file will be saved
	        File saveFile = new ClassPathResource("static/uploads").getFile();
	        // Create the path for the file, including the random number and original filename
	        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + randomNumber + file.getOriginalFilename());

	        // Ensure the directory exists, and create it if it doesn't
	        if (!Files.exists(saveFile.toPath())) {
	            Files.createDirectories(saveFile.toPath());
	        }

	        // Copy the file to the specified path, replacing any existing file
	        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	    } catch (Exception e) {
	        // Return false if an exception occurs
	        return false;
	    }
	    // Return true if the file is successfully stored
	    return true;
	}



	// Store product in the database
	@Override
	public boolean addProduct(Products product) {
	    try {
	        // Calculate the price after discount
	        int priceAfterDiscount = product.getPrice() - (product.getPrice() * product.getDiscount()) / 100;
	        product.setPriceAfterDiscount(priceAfterDiscount);

	        // Save the product to the repository
	        repo.save(product);
	    } catch (Exception e) {
	        // Print stack trace for debugging and return false if an exception occurs
	        e.printStackTrace();
	        return false;
	    }
	    // Return true if the product is successfully stored
	    return true;
	}



	//Get all products
	@Override
	public List<Products> getAllProducts() {
		return (List<Products>) repo.findAll();
	}


	//Delete product by product id
	@Override
	public String deleteProductById(Integer id) {
		repo.deleteById(id);
		return "product deleted successfully";
	}


	// Find a product by its product id
	@Override
	public Products findProductById(Integer productId) {
	    // Retrieve product  using the provided id
	    Optional<Products> op = repo.findById(productId);

	    // Check if the product is present
	    if (op.isPresent()) {
	        // Get and return the product object
	        Products product = op.get();
	        return product;
	    }

	    // Return null if the product is not found
	    return null;
	}



	// Update product details
	@Override
	public boolean updateProduct(Products product, MultipartFile file) {
		
	    if (file.isEmpty()) {//If no new file is provided
	        //Get product by product id
	        Optional<Products> prod = repo.findById(product.getId());

	        if (prod.isPresent()) {//If product is available
	            // Get the current image path and set it to the product
	            String imagePath = prod.get().getImage();
	            product.setImage(imagePath);
	        }

	    } else {
	        // Generate a random number for the new image name
	        Integer random = new Random().nextInt(100);
	        // Set the new image name to the product
	        product.setImage(random + file.getOriginalFilename());
	        // Store the new product image in the local folder
	        storeProductImage(file, random);
	    }

	    // Calculate and set the price after discount
	    int priceAfterDiscount = product.getPrice() - (product.getPrice() * product.getDiscount()) / 100;
	    product.setPriceAfterDiscount(priceAfterDiscount);

	    try {
	        // Save the updated product to the repository
	        repo.save(product);
	    } catch (Exception e) {
	        // Return false if an exception occurs during save operation
	        return false;
	    }
	    // Return true if the product is successfully updated
	    return true;
	}



	//Get all products by category id
	@Override
	public List<Products> getAllProductsByCategoryId(Integer id) {
		return repo.findByCategoryType(id);
	}


	//Get latest three products
	@Override
	public List<Products> getLatestThreeProducts() {
		// Create a Pageable object to request the first 3 products
		Pageable pageable = (Pageable) PageRequest.of(0, 3);
		// Retrieve and return the top latest 3 products
		return repo.findTop3LatestProducts(pageable);
	}


	//Get top 4 deals products
	@Override
	public List<Products> getTopDealsFourProducts() {
		// Create a Pageable object to request the first 4 products
		Pageable pageable = PageRequest.of(0, 4);
		// Retrieve and return the top 4 deal products
		return repo.findTop4DealsProducts(pageable);
	}


}
