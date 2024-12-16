package org.vdb.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vdb.blog.services.UserService;
import org.vdb.payloads.ApiResponse;
import org.vdb.payloads.UserDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserControllers {

	
	@Autowired
	private UserService userService;
	
	// Post-create User
	@PostMapping("/")
	public ResponseEntity<UserDto> creteUsers(@Valid @RequestBody UserDto userDto)
	{
		UserDto createdUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	// Put- update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid	 @RequestBody UserDto userDto,@PathVariable("userId")Integer uid)
	{
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		
		return ResponseEntity.ok(updateUser);
	}
	
	// Delete- delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Integer userid)
	{
		this.userService.deleteUser(userid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	// Get- get All User
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		return ResponseEntity.ok(this.userService.getAllUsers());
		
	}
	
	// Get- get Single User
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId")Integer userid)
	{
		return ResponseEntity.ok(this.userService.getUserById(userid));
			
	}
	
}
