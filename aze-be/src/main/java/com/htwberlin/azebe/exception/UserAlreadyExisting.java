package com.htwberlin.azebe.exception;

public class UserAlreadyExisting extends RuntimeException {

    public UserAlreadyExisting(String msg) {
        super(msg);
    }
}
