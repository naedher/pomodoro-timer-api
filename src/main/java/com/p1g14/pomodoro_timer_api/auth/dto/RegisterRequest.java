package com.p1g14.pomodoro_timer_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterRequest {
    @NotBlank
    @Email(message = "Must be a valid email")
    private String email;
    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
