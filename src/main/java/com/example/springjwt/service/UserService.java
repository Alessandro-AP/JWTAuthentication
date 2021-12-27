package com.example.springjwt.service;

import com.example.springjwt.model.User;
import com.example.springjwt.model.Role;

import java.util.List;

public interface UserService {
    User saveUser(User user); // Save user in db.
    Role saveRole(Role role) throws Exception; // Save role in db.
    void addRoleToUser(String username, String roleName); // Add role to user.
    User getUser(String username); // Get user by username
    List<User> getUsers(); // get all users (bad if we have a lot of users, better return 'n' users at a time)
}
