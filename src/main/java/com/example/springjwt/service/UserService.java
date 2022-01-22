package com.example.springjwt.service;

import com.example.springjwt.model.User;
import com.example.springjwt.model.Role;

import java.util.List;

/**
 * This service offers the following functionalities:
 *  - Save user in db
 *  - Save role in db
 *  - Add role to user
 *  - Get user by username
 *  - Get all users
 */
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role) throws Exception;
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
