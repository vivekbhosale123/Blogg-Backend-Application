package org.vdb.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vdb.blog.entities.Category;
import org.vdb.blog.exceptions.ResourceNotFoundException;
import org.vdb.blog.repositories.CategoryRepository;
import org.vdb.blog.services.CategoryService;
import org.vdb.payloads.CategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat = this.modelMapper.map(categoryDto,Category.class);
		Category addedCat = this.categoryRepository.save(cat);
		return this.modelMapper.map(addedCat,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Catgory Id", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcat = this.categoryRepository.save(cat);
		
		return this.modelMapper.map(updatedcat,categoryDto.getClass());
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
	  Category cat=this.categoryRepository.findById(categoryId).
	  orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
	  
	   this.categoryRepository.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category cat= this.categoryRepository.findById(categoryId).
		orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
	    List<Category> categories = this.categoryRepository.findAll();
	    List<CategoryDto> listOfCategories = categories.stream()
	        .map(category -> this.modelMapper.map(category, CategoryDto.class))
	        .collect(Collectors.toList());
	    
	    return listOfCategories;
	}


}
