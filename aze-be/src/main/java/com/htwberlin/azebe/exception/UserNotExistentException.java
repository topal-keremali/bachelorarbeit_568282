package com.htwberlin.azebe.exception;

/**
 *  User not existent exception.
 */
public class UserNotExistentException extends RuntimeException {
    /**
     * Instantiates a new User not existent exception.
     *
     * @param msg the msg
     */
    public UserNotExistentException(String msg) {
        super(msg);
    }
}
