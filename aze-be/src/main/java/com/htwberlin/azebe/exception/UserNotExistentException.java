package com.htwberlin.azebe.exception;

public class UserNotExistentException extends RuntimeException {
    public UserNotExistentException(String msg) {
        super(msg);
    }
}
