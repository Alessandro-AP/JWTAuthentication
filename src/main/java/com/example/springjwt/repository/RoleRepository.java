package com.example.springjwt.repository;

import com.example.springjwt.model.User;
import com.example.springjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String email);
}