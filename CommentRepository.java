package org.vdb.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vdb.blog.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
