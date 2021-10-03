package com.htwberlin.azebe.security;

/**
 * Security Constans for Tokengeneration and sign up url
 */
public class SecurityConstants {

    private SecurityConstants() {

    }

    public static final String SECRET = "EVgYUyV_LQVUq4YZyRG1un"; //akt. so
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}

