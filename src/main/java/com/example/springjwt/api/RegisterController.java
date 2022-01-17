package com.example.springjwt.api;


import com.example.springjwt.exception.InvalidPasswordException;
import com.example.springjwt.exception.UserAlreadyExistAuthenticationException;
import com.example.springjwt.model.User;
import com.example.springjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * This controller is responsible for managing and
 * processing a user's inscription requests.
 */
@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @RequestMapping(value="/register", method= RequestMethod.POST,produces="application/json")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        User newUser;
        JSONObject jsonResponse = new JSONObject();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
        try{
            newUser = userService.saveUser(user);
            jsonResponse.put("id", newUser.getId());
            jsonResponse.put("username", newUser.getUsername());
            jsonResponse.put("role", newUser.getRoles().stream().findFirst().get().getName());
        }
        catch (UserAlreadyExistAuthenticationException e){
            jsonResponse.put("error", e.getMessage());
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResponse.toString());
        }
        catch (InvalidPasswordException e) {
            JSONObject errors = new JSONObject();
            errors.put("property", "password");
            errors.put("message", e.getMessage());
            JSONArray test = new JSONArray();
            test.put(errors);
            jsonResponse.put("errors",test );
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(jsonResponse.toString());
        }
        return  ResponseEntity.created(uri).body(jsonResponse.toString());
    }
}
