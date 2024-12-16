package org.vdb.payloads;

import java.util.HashSet;
import java.util.Set;

import org.vdb.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4,message = "username name must be min of characters !!")
	private String name;
	
	@Email(message = "Email Address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10 ,message = "password must be min of 4 chars and max of 10 chars !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
