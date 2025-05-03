package com.p1g14.pomodoro_timer_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Not a valid email adress")
    private String email;
    @NotBlank(message = "Password can not be blank")
    private String password;
}
