package com.htwberlin.azebe.exception;

/**
 * Jwt exception.
 */
public class JwtException extends RuntimeException {
    /**
     * Instantiates a new Jwt exception.
     *
     * @param message the message
     */
    public JwtException(String message) {
        super(message);
    }
}
