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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nt.model.Category;
import com.nt.repository.ICategoryRepo;



@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryRepo repo;

	//Store category image into local folder
	@Override
	public boolean storeCategoryImage(MultipartFile file, Integer randomNumber) {
		try {
			// Get the directory where images will be saved (relative to the classpath)
			File saveFile=new ClassPathResource("static/uploads").getFile();
			// Generate the file path where the image will be saved
			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+ randomNumber+file.getOriginalFilename());
			// Check if the directory exists, and create it if it doesn't
			if (!Files.exists(saveFile.toPath())) {
				Files.createDirectories(saveFile.toPath());
			}

			// Copy the file to the target location, replacing any existing file
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e){
			// If an error occurs, return false indicating the file was not stored
			return false;
		}

		// Return true if the file was successfully stored
		return true;
	}

	//Store file name and image path in database
	@Override
	public boolean addCategory(String categoryName, String imagePath) {
		//Create Category object and set category name  & image path 
		Category cat=new Category();
		cat.setName(categoryName);
		cat.setFilePath(imagePath);

		try {
			//Store category details to ddatabase
			repo.save(cat);
		}catch(Exception e) {
			return false; //Failed to store category details
		}

		return true ; //Category details saved successfully
	}

	//Update category details
	@Override
	public boolean updateCategory(Category category, MultipartFile file) {
       boolean flag=true;
		if(file.isEmpty()) { //If file is empty
			//Get Category by category id
			Optional<Category> cat=repo.findById(category.getId());

			if(cat.isPresent()) {//If category is present
				//Get previous image path
				String path=cat.get().getFilePath();
				//Set image path to updated Category object
				category.setFilePath(path);
			}

		}else {
			//Generate random numer
			Integer randomNumber=new Random().nextInt(100);
			//Set new image path to updated Category object
			category.setFilePath(randomNumber+file.getOriginalFilename());
			//Store category image to local folder
			flag=storeCategoryImage(file, randomNumber);
		}


		try {
			if(flag)//if new image is failed to store at local folder then it will not execute
			repo.save(category);
		}catch(Exception e) {
			return false; //failed to update category details
		}
		return true ; //successfully updated category details
	}

	//Get all category
	@Override
	public List<Category> getAllCategory() {
		return (List<Category>) repo.findAll();
	}
	
	//Delete category by category id
	@Override
	public String deleteCategoryById(Integer categoryId) {
		repo.deleteById(categoryId);
		return "category is deleted succefully";
	}

	//Find category by category id
	@Override
	public Category findCategoryById(Integer categoryId) {
		//Get category by category id
		Optional<Category> cat=repo.findById(categoryId);

		if (cat.isPresent()) {//If category is available
			//get category object
			Category category= cat.get();
			//return category
			return category;
		}else { 
			//return null
			return null;
		}
	}

	//Get all category Types
	@Override
	public HashMap<Integer, String> getAllCategoryTypes() {
		HashMap<Integer, String> hashMap=new HashMap<>();
		//Get all Category List
		List<Category> categoryList=(List<Category>) repo.findAll();
		
		//Store category id & category name into hashmap
		for(Category category:categoryList) {
			hashMap.put(category.getId(), category.getName());
		}
		return hashMap;
	}
}
