package org.vdb.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.vdb.blog.config.AppConstants;
import org.vdb.blog.services.FileService;
import org.vdb.blog.services.PostService;
import org.vdb.payloads.ApiResponse;
import org.vdb.payloads.PostDto;
import org.vdb.payloads.PostResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;
	
	@Value("project.image")
	private String path;
	
	// create Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId) {
		PostDto post = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
	}

	// get Post by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId) {
		List<PostDto> posts = this.postService.getPostByUser(userId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	// get Post by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	// get single post
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId")Integer postId)
	{
		PostDto singlePost = this.postService.getSinglePost(postId);
		
		return new ResponseEntity<PostDto>(singlePost,HttpStatus.OK);
	}
	
	// get All posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "PageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer PageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer PageSize,
			@RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR,required = false)String sortDir
			)
	{
		PostResponse postResponse = this.postService.getAllPosts(PageNumber, PageSize,sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// delete post
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId")Integer postId)
	{
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", false),HttpStatus.OK);
	}
	
	// update Post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId")Integer postId)
	{
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	// search Post by title
	@GetMapping("/posts/search/{keywords}")	
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords")String keywords)
	{
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
	
	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile file,
			@PathVariable Integer postId
			) throws IOException
	{
		PostDto postDto= this.postService.getSinglePost(postId);
		
		String fileName = this.fileService.uploadImage(path, file);
		
		postDto.setImageName(fileName);
		
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	// method to serve files
    @GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,
			HttpServletResponse response)throws IOException
	{
		InputStream resource = this.fileService.getsource(path, imageName);
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    StreamUtils.copy(resource,response.getOutputStream());
	}
}
