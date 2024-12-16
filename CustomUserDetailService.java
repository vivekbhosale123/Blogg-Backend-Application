package org.vdb.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vdb.blog.entities.User;
import org.vdb.blog.exceptions.ResourceNotFoundException;
import org.vdb.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email"+username, 0));
		
		return user;
	}

}
