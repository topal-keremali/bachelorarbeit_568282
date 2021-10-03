package com.htwberlin.azebe.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.htwberlin.azebe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.htwberlin.azebe.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private Logger jwtLogger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    private UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationContext context) {
        super(authManager);
        this.userRepository = context.getBean(UserRepository.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        try {

            String token = request.getHeader(HEADER_STRING);
            if (token != null) {
                // parse the token.
                String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
                var user = userRepository.findByUsername(username);
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                }

                return null;
            }

            return null;

        } catch (SignatureVerificationException e) {
            jwtLogger.warn("Request with invalid JWT was made.");
            return null;
        }
    }
}
