package org.vdb.blog.services;

import java.util.List;

import org.vdb.blog.entities.Post;
import org.vdb.payloads.PostDto;
import org.vdb.payloads.PostResponse;

public interface PostService {

	// create Post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	// update post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	// delete post
	void deletePost(Integer postId);
	
	// get single post
	PostDto getSinglePost(Integer postId);
	
	// get all post
	PostResponse getAllPosts(Integer PageNumber,Integer pageSize,String sortBy,String sortDir);
	
	// get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// get all post by User	
	List<PostDto> getPostByUser(Integer userId);
	
	//search Posts
	List<PostDto> searchPosts(String keyword);
	
	
}
