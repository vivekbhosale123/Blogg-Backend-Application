package org.vdb.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vdb.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
