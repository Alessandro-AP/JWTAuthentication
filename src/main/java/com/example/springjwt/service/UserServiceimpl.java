package com.example.springjwt.service;

import com.example.springjwt.exception.InvalidPasswordException;
import com.example.springjwt.exception.UserAlreadyExistAuthenticationException;
import com.example.springjwt.model.Role;
import com.example.springjwt.model.User;
import com.example.springjwt.repository.RoleRepository;
import com.example.springjwt.repository.UserRepository;
import com.example.springjwt.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor //Create constructor for repositories
// Creates a proxy that implements the same interface(s) of the class.
// The calls are intercepted and the behaviors injected via the proxy mechanism.
@Transactional
@Slf4j // For logs
public class UserServiceimpl implements  UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else{
            log.info("User found in database {}" , username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        if (userRepo.findByUsername(user.getUsername()) != null){
            log.error("The User already exist in database");
            throw new UserAlreadyExistAuthenticationException("The username already exists");
        }
        else if(!PasswordValidator.isValidPassword(user.getPassword())){
            log.error("Password doesn't respect the minimum requirements.");
            throw new InvalidPasswordException("The password does not match the security politics, it should be at least 8 char long, should contain at least one uppercase char, one lowercase char, one digit and one special character");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        addRoleToUser(user.getUsername(), "user");
        return savedUser;
    }

    @Override
    public Role saveRole(Role role) throws Exception {
        log.info("Saving new role {} to the database", role.getName());
        if(roleRepo.findByName(role.getName()) != null){
            log.error("The role already exist in database");
            throw new Exception("The role already exist in database");
        }
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}" , roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);

        if(!user.getRoles().contains(role))
            user.getRoles().add(role);
        else
            log.error("Role already assigned to user");
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
