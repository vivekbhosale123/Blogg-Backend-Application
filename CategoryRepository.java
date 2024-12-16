package org.vdb.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vdb.blog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{

}
