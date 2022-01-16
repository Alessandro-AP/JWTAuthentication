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

    /**
     * BCryptPasswordEncoder implementation.
     * Uses the bcrypt algorithm to hash the passwords.
     * @return the encoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Function executing at the launch of the application,
     * inserts some basic data in the mysql database
     */
    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            //If the database is not empty, default data is not added
            if(userService.getUser("admin") == null) {
                userService.saveRole(new Role(null, "user"));
                userService.saveRole(new Role(null, "admin"));
                userService.saveUser(new User(null, "admin", "Xkxtxyglu@z9Afacb-k1", new ArrayList<>()));
                userService.addRoleToUser("admin", "admin");
            }
        };
    }
}
