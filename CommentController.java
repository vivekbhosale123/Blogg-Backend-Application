package org.vdb.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vdb.blog.services.CommentService;
import org.vdb.payloads.ApiResponse;
import org.vdb.payloads.CommentDto;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// create Comments
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComments(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId) {
		CommentDto comment = this.commentService.createComment(commentDto, postId);

		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}

	// Delete Comments
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComments(
			@PathVariable("commentId") Integer commentId) {
		
		this.commentService.deleteComment(commentId);
		
	    return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", false),HttpStatus.OK);
	}
}
