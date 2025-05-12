package com.p1g14.pomodoro_timer_api.auth;

import com.p1g14.pomodoro_timer_api.auth.dto.LoginRequest;
import com.p1g14.pomodoro_timer_api.auth.dto.AuthResponse;
import com.p1g14.pomodoro_timer_api.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing authentication.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //Spring will automatically trigger bean‐validation when we annotate the @RequestBody parameter with @Valid
    /**
     * Register a new user
     * @param request the credentials of the user
     * @return HTTP 200 OK response containing a JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Log in a user
     * @param request the credentials of the user
     * @return HTTP 200 OK response containing a JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
