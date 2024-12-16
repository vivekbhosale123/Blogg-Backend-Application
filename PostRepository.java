package org.vdb.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vdb.blog.entities.Category;
import org.vdb.blog.entities.Post;
import org.vdb.blog.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{


	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
//	@Query("select p from post p where p.title like :key")
//	List<Post> findByTitle(@Param("key")String title);
	
	List<Post> findByTitleContaining(String title);
}
