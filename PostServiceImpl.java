package org.vdb.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vdb.blog.entities.Category;
import org.vdb.blog.entities.Post;
import org.vdb.blog.entities.User;
import org.vdb.blog.exceptions.ResourceNotFoundException;
import org.vdb.blog.repositories.CategoryRepository;
import org.vdb.blog.repositories.PostRepository;
import org.vdb.blog.repositories.UserRepository;
import org.vdb.blog.services.PostService;
import org.vdb.payloads.CategoryDto;
import org.vdb.payloads.PostDto;
import org.vdb.payloads.PostResponse;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
    private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id", userId));
		
		Category category= this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepository.save(post);
		
		return this.modelMapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setAddedDate(postDto.getAddedDate());
		
		Post postUpdate= this.postRepository.save(post);
		
		return this.modelMapper.map(postUpdate, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepository.findById(postId).
				orElseThrow(()->new ResourceNotFoundException("Post","Post Id", postId));
	
		this.postRepository.delete(post);
	}

	@Override
	public PostDto getSinglePost(Integer postId) {
	
		Post posts = this.postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","Post Id", postId));
		
		return this.modelMapper.map(posts,PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer PageNumber,Integer pageSize,String sortBy,String sortDir) {
	
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
		
//		Pageable p=PageRequest.of(PageNumber,pageSize,Sort.by(sortBy).descending());
		Pageable p=PageRequest.of(PageNumber,pageSize,sort);

		Page<Post> pagePosts = this.postRepository.findAll(p);
		
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postList = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
		.collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postList);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
	
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
		
		List<Post> posts = this.postRepository.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
		.collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id", userId));
		
		List<Post> posts = this.postRepository.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).
		collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
//	
//		List<Post> post = this.postRepository.findByTitleContaining("%"+keyword+"%");
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
		.collect(Collectors.toList());
		
		return postDtos;
	}

	
}
