package org.vdb.blog.services;

import java.util.List;

import org.vdb.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userId);
	
	UserDto getUserById(Integer UserId);
	
	List<UserDto>getAllUsers();
	
	void deleteUser(Integer userId);
}
