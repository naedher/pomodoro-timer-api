package com.p1g14.pomodoro_timer_api.auth.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
}
