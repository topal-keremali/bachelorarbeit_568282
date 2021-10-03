package com.htwberlin.azebe.exception;

/**
 * Exception for Unauthorized Request.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Instantiates a new Unauthorized exception.
     *
     * @param msg the msg
     */
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
