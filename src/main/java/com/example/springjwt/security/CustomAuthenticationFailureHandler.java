package com.example.springjwt.security;

import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class handles failed authentications,
 * when a user authentications fails method onAuthenticationFailure is called.
 */
public class CustomAuthenticationFailureHandler  implements AuthenticationFailureHandler {
    /**
     * Send a JSON error message to the client.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        JSONObject jsonError = new JSONObject();
        jsonError.put("error", "Invalid username or password");

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.print(jsonError);
    }
}