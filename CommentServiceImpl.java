package org.vdb.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vdb.blog.entities.Comment;
import org.vdb.blog.entities.Post;
import org.vdb.blog.exceptions.ResourceNotFoundException;
import org.vdb.blog.repositories.CommentRepository;
import org.vdb.blog.repositories.PostRepository;
import org.vdb.blog.services.CommentService;
import org.vdb.payloads.CommentDto;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		
	    Comment comment = this.modelMapper.map(commentDto,Comment.class);
	    
	    comment.setPost(post);
	    
	    Comment comment2 = this.commentRepository.save(comment);
		
		return this.modelMapper.map(comment2,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
	 
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment Id", commentId));
	   
		this.commentRepository.delete(comment);
	}

}
