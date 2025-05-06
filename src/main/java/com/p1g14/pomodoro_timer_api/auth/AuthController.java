package com.p1g14.pomodoro_timer_api.auth;

import com.p1g14.pomodoro_timer_api.auth.dto.LoginRequest;
import com.p1g14.pomodoro_timer_api.auth.dto.AuthResponse;
import com.p1g14.pomodoro_timer_api.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //Spring will automatically trigger bean‐validation when we annotate the @RequestBody parameter with @Valid

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request succeeded. The response contains the requested data."),
            @ApiResponse(responseCode = "400", description = "The request is invalid or malformed. Check input parameters or data format."),
            @ApiResponse(responseCode = "404", description = "The requested resource could not be found. It may not exist or the path is incorrect."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server. Please try again later.")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request succeeded. The response contains the requested data."),
            @ApiResponse(responseCode = "400", description = "The request is invalid or malformed. Check input parameters or data format."),
            @ApiResponse(responseCode = "404", description = "The requested resource could not be found. It may not exist or the path is incorrect."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server. Please try again later.")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
