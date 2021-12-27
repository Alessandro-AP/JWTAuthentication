package com.example.springjwt.service;

import com.example.springjwt.model.User;
import com.example.springjwt.model.Role;

import java.util.List;

public interface UserService {
    User saveUser(User user); //save user in db
    Role saveRole(Role role); //save role in db
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers(); // bad if we have a lot of users (better return 20 users at a time)
}
