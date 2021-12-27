package com.example.springjwt.repository;

import com.example.springjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> { //< class , primary key type >
    User findByUsername(String username);
}
