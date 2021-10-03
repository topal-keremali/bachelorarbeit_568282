package com.htwberlin.azebe.security;

/**
 * Security Constans for Tokengeneration and sign up url
 */
public class SecurityConstants {

    private SecurityConstants() {

    }

    /**
     * The constant SECRET.
     */
    public static final String SECRET = "EVgYUyV_LQVUq4YZyRG1un"; //akt. so
    /**
     * The constant TOKEN_PREFIX.
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * The constant HEADER_STRING.
     */
    public static final String HEADER_STRING = "Authorization";
    /**
     * The constant SIGN_UP_URL.
     */
    public static final String SIGN_UP_URL = "/users/sign-up";
}

