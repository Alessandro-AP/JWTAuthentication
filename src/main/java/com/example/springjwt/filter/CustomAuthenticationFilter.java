package com.example.springjwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springjwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This filter is used to handle authentications and
 * provides a POST endpoint to the "/login" route.
 * It tries to find a username and password in the body of request and
 * if it finds them, tries to authenticate the user with those values.
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,AuthenticationFailureHandler authenticationFailureHandler, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    /**
     * This method is called when the client makes an authentication attempt at the "/login" route.
     * To perform a login it is necessary to send a JSON object containing "username" and "password" fields.
     * If authentication succeeds the underlying method successfulAuthentication is called.
     * If the authentication fails the AuthenticationFailureHandler is called for handle the error.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String data = "";
        // Retrieves JSON credentials from body
        try {
             data = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject dataJson = new JSONObject(data);
        String username = dataJson.getString("username");
        String password = dataJson.getString("password");

        log.info("Username is : {}", username);
        log.info("Password id : {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Successful authentication management
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        org.springframework.security.core.userdetails.User userDetails = (User)authentication.getPrincipal();

        String SECRET_KEY = "czvFbg2kmvqbcu(7Ux+c";

        // Create the JWT token based on user.
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        com.example.springjwt.model.User user = userService.getUser(userDetails.getUsername());
        Map<String, Object> tokens = new HashMap<>();
        Map<String, Object> account = new HashMap<>();

        // Create the JSON response sent to the client
        account.put("id",user.getId());
        account.put("username",user.getUsername());
        if(user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "admin")))
            account.put("role","admin");
        else
            account.put("role","user");
        tokens.put("token", access_token);
        tokens.put("account", account);

        // Sent< JSON response to client
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
