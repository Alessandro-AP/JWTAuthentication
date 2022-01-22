package com.example.springjwt.repository;

import com.example.springjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository implementing data access layers for roles in database.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String email);
}