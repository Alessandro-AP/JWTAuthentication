package com.example.springjwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Custom exception class for invalid password errors.
 */
public class InvalidPasswordException extends AuthenticationException {
    public InvalidPasswordException(final String msg) {
        super(msg);
    }
}