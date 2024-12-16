package org.vdb.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vdb.blog.config.AppConstants;
import org.vdb.blog.entities.Role;
import org.vdb.blog.entities.User;
import org.vdb.blog.exceptions.ResourceNotFoundException;
import org.vdb.blog.repositories.RoleRepository;
import org.vdb.blog.repositories.UserRepository;
import org.vdb.blog.services.UserService;
import org.vdb.payloads.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.dtoToUser(userDto);
		
		User save = this.userRepository.save(user);
		
		return this.UserToDto(save);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepository.findById(userId).
		orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepository.save(user);
		
		UserDto usDto1 = this.UserToDto(updatedUser);
		
		return usDto1;
	}

	@Override
	public UserDto getUserById(Integer UserId) {
		
		User user = this.userRepository.findById(UserId).
		orElseThrow(()->new ResourceNotFoundException("User","Id",UserId));
		
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users= this.userRepository.findAll();
		
		List<UserDto> userDtos = users.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
	
		User user = this.userRepository.findById(userId).
				
		orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		this.userRepository.delete(user);
	}
	
	private User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto,User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}

	private UserDto UserToDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto,User.class);
		
		// encode with password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepository.save(user);
		
		return this.modelMapper.map(newUser,UserDto.class);
	}
}
