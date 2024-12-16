package org.vdb.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vdb.blog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);
}
