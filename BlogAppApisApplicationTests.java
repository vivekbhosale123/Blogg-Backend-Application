package org.vdb.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vdb.blog.repositories.UserRepository;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest()
	{
		String className = this.userRepository.getClass().getName();
	    String packageName = this.userRepository.getClass().getPackageName();
	    System.out.println(className);
	    System.out.println(packageName);
	}
	
}
