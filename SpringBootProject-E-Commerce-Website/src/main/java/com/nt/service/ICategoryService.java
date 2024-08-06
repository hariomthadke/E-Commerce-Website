package com.nt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nt.model.Category;

public interface ICategoryService {

	//Store category image into local folder
	public boolean storeCategoryImage(MultipartFile file, Integer randomNumber);
	//Store file name and image path in database
	public boolean addCategory(String categoryName, String imagePath);
	//Update category
	public boolean updateCategory(Category category, MultipartFile file);
	//Get all category
	public List<Category> getAllCategory();
	//Delete category by category id
	public String deleteCategoryById(Integer categoryId);
	//Find category by category id
	public Category findCategoryById(Integer categoryId);
	//Get all Category Types
	public HashMap<Integer, String> getAllCategoryTypes();

}
