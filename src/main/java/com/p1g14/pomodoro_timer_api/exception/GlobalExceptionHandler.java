package com.p1g14.pomodoro_timer_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * The global exception handler for the application.
 * Captures and formats various exceptions into standardized HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles authentication failures due to invalid credentials.
     *
     * @param ex the {@link BadCredentialsException} thrown by Spring Security
     * @return a response entity with an error message and HTTP 401 status
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
    }

    /**
     * Handles cases where a user cannot be found by username.
     *
     * @param ex the UsernameNotFoundException thrown during authentication
     * @return a response entity with an error message and HTTP 404 status
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
    }

    /**
     * Handles application-specific resource not found errors.
     *
     * @param ex the custom ResourceNotFoundException
     * @return a response entity with an error message and HTTP 404 status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Resource not found"));
    }

    /**
     * Handles access denied exceptions when users try to access unauthorized resources.
     *
     * @param ex the AccessDeniedException thrown by Spring Security
     * @return a response entity with an error message and HTTP 403 status
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Access denied"));
    }

    /**
     * Handles illegal argument exceptions, typically thrown for invalid method arguments.
     *
     * @param ex the IllegalArgumentException
     * @return a response entity with an error message and HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles malformed JSON input errors.
     *
     * @param ex the HttpMessageNotReadableException thrown when request JSON is invalid
     * @return a response entity with an error message and HTTP 400 status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid request format"));
    }

    /**
     * Handles validation errors for @Valid annotated request bodies.
     * Extracts and formats all field-level validation messages.
     *
     * @param ex the MethodArgumentNotValidException containing validation errors
     * @return a response entity with a map of field errors and HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage())
        );
        Map<String, Object> body = Map.of(
                "timestamp",  LocalDateTime.now(),
                "status",     HttpStatus.BAD_REQUEST.value(),
                "errors",     fieldErrors
        );
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handles any unexpected exception not handled by other handlers
     * @param ex the Exception
     * @return a response entity with an error message and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Something went wrong"));
    }

    /**
     * Handles exception when a user tries to register with an email that already exists
     * @param ex the {@link EmailAlreadyExistsException}
     * @return a response entity with an error message and HTTP 409 status
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }
}
