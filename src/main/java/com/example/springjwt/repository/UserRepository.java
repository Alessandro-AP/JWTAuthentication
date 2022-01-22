package com.example.springjwt.repository;

import com.example.springjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository implementing data access layers for users in database.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
