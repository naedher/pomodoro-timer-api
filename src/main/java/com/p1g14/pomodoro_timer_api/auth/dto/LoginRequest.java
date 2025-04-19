package com.p1g14.pomodoro_timer_api.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}
