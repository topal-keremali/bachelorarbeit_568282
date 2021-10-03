package com.htwberlin.azebe.exception;

/**
 * Exception for User already existing.
 */
public class UserAlreadyExisting extends RuntimeException {

    /**
     * Instantiates a new User already existing.
     *
     * @param msg the msg
     */
    public UserAlreadyExisting(String msg) {
        super(msg);
    }
}
