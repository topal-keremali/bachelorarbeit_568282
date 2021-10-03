package com.htwberlin.azebe.security;


import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htwberlin.azebe.exception.UnauthorizedException;
import com.htwberlin.azebe.model.User;
import com.htwberlin.azebe.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.htwberlin.azebe.security.SecurityConstants.*;

/**
 * The type Jwt authentication filter.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    /**
     * Instantiates a new Jwt authentication filter.
     *
     * @param authenticationManager the authentication manager
     * @param context               the context
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        this.userRepository = context.getBean(UserRepository.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new UnauthorizedException("internal problem");
        } catch (InternalAuthenticationServiceException e) {
            throw new UsernameNotFoundException("User existiert nicht");
        }
    }

    /**
     * Creates new JWT with successfull Authentication
     *
     * @param req   http-request
     * @param res   http-response
     * @param chain filterchain
     * @param auth  auth object
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        var user = userRepository.findByUsername(auth.getName());

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("ur", user.getUsername() + "@" + user.getName())
                .withClaim("name", user.getName())
                .withClaim("id", user.getId())
                .withIssuer("aze-be")
                .sign(HMAC512(SECRET));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

    }
}
