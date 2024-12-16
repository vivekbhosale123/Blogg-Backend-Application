package org.vdb.blog.services;

import java.util.List;

import org.vdb.payloads.CategoryDto;

public interface CategoryService {

	// create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update category
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

	// delete Category
	void deleteCategory(Integer categoryId);

	// get single category
	CategoryDto getCategory(Integer categoryId);

	// get All category
	List<CategoryDto> getAllCategory();

}
