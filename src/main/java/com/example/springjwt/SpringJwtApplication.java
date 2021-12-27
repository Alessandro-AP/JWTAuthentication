package com.example.springjwt;

import com.example.springjwt.model.Role;
import com.example.springjwt.model.User;
import com.example.springjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
          userService.saveRole(new Role(null,"user"));
          userService.saveRole(new Role(null,"admin"));

          userService.saveUser(new User(null,"alessandro","1234",new ArrayList<>()));
          userService.saveUser(new User(null,"alessandro2","1234",new ArrayList<>()));
          userService.saveUser(new User(null,"alessandro3","1234",new ArrayList<>()));

          userService.addRoleToUser("alessandro", "admin");
          userService.addRoleToUser("alessandro2", "user");
          userService.addRoleToUser("alessandro3", "user");

        };
    }
}
