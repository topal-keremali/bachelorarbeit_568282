package com.htwberlin.azebe.controller;

import com.htwberlin.azebe.exception.NoOpenShiftException;
import com.htwberlin.azebe.exception.UnauthorizedException;
import com.htwberlin.azebe.exception.UserAlreadyExisting;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  Global exception handler for the REST-Api, to send Exception in error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private class JsonResponse {

        /**
         * The Timestamp.
         */
        String timestamp;

        /**
         * The Message.
         */
        String message;

        /**
         * The Http status.
         */
        int httpStatus;

        /**
         * Instantiates a new Json response.
         */
        public JsonResponse() {
        }

        /**
         * Instantiates a new Json response.
         *
         * @param timestamp  the timestamp
         * @param message    the message
         * @param httpStatus the http status
         */
        public JsonResponse(String timestamp, String message, int httpStatus) {
            super();
            this.timestamp = timestamp;
            this.message = message;
            this.httpStatus = httpStatus;
        }

        /**
         * Gets message.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets message.
         *
         * @param message the message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Gets http status.
         *
         * @return the http status
         */
        public int getHttpStatus() {
            return httpStatus;
        }

        /**
         * Sets http status.
         *
         * @param httpStatus the http status
         */
        public void setHttpStatus(int httpStatus) {
            this.httpStatus = httpStatus;
        }

        /**
         * Gets timestamp.
         *
         * @return the timestamp
         */
        public String getTimestamp() {
            return timestamp;
        }

        /**
         * Sets timestamp.
         *
         * @param timestamp the timestamp
         */
        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    /**
     * Handle user existiert bereits exception response entity.
     *
     * @param req the req
     * @param ex  the ex
     * @return the response entity
     */
    @ExceptionHandler(UserAlreadyExisting.class)
    public ResponseEntity<JsonResponse> handleUserExistiertBereitsException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "User existiert bereits", 406), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle user existiert nicht exception response entity.
     *
     * @param req the req
     * @param ex  the ex
     * @return the response entity
     */
    @ExceptionHandler(NoOpenShiftException.class)
    public ResponseEntity<JsonResponse> handleUserExistiertNichtException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "Keine offe Schicht", 406), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle unauthorized exception response entity.
     *
     * @param req the req
     * @param ex  the ex
     * @return the response entity
     */
    @ExceptionHandler({UnauthorizedException.class, AccessDeniedException.class})
    public ResponseEntity<JsonResponse> handleUnauthorizedException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "Fehlende Rechte für diese Anfrage", 403), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handle not found exception response entity.
     *
     * @param req the req
     * @param res the res
     * @param ex  the ex
     * @return the response entity
     */
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handleNotFoundException(HttpServletRequest req, HttpServletResponse res, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "Anfrage kann nicht verarbeitet werden", 400), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle user not found exception response entity.
     *
     * @param req the req
     * @param res the res
     * @param ex  the ex
     * @return the response entity
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handleUserNotFoundException(HttpServletRequest req, HttpServletResponse res, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "User not found", 404), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
