package com.example.springjwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Custom exception class for UserAlreadyExist errors.
 */
public class UserAlreadyExistAuthenticationException extends AuthenticationException {
    public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }
}