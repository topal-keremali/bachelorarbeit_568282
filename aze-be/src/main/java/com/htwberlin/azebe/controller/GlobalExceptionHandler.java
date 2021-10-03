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

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private class JsonResponse {

        String timestamp;

        String message;

        int httpStatus;

        public JsonResponse() {
        }

        public JsonResponse(String timestamp, String message, int httpStatus) {
            super();
            this.timestamp = timestamp;
            this.message = message;
            this.httpStatus = httpStatus;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(int httpStatus) {
            this.httpStatus = httpStatus;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    @ExceptionHandler(UserAlreadyExisting.class)
    public ResponseEntity<JsonResponse> handleUserExistiertBereitsException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "User existiert bereits", 406), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoOpenShiftException.class)
    public ResponseEntity<JsonResponse> handleUserExistiertNichtException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "Keine offe Schicht", 406), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnauthorizedException.class, AccessDeniedException.class})
    public ResponseEntity<JsonResponse> handleUnauthorizedException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "Fehlende Rechte für diese Anfrage", 403), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handleNotFoundException(HttpServletRequest req, HttpServletResponse res, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "Anfrage kann nicht verarbeitet werden", 400), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handleUserNotFoundException(HttpServletRequest req, HttpServletResponse res, Exception ex) {
        return new ResponseEntity<>(
                new JsonResponse(LocalDateTime.now().format(format), "User not found", 404), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
