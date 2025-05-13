package com.p1g14.pomodoro_timer_api.auth;

import com.p1g14.pomodoro_timer_api.auth.dto.LoginRequest;
import com.p1g14.pomodoro_timer_api.auth.dto.AuthResponse;
import com.p1g14.pomodoro_timer_api.auth.dto.RegisterRequest;
import com.p1g14.pomodoro_timer_api.config.JwtService;

import com.p1g14.pomodoro_timer_api.exception.EmailAlreadyExistsException;

import com.p1g14.pomodoro_timer_api.timer.Timer;
import com.p1g14.pomodoro_timer_api.timer.TimerMapper;
import com.p1g14.pomodoro_timer_api.timer.TimerRepository;
import com.p1g14.pomodoro_timer_api.timer.dto.TimerCreateRequest;

import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TimerRepository timerRepository;
    private final TimerMapper timerMapper;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("User with email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        setDefaultTimers(user);

        String jwt = jwtService.generateToken(user);

        return new AuthResponse(jwt);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
        /**
         * Creates three standard Pomodoro timers for the given user.
         */

    }
    public void setDefaultTimers (User user){
        List<TimerCreateRequest> presets = List.of(
                TimerCreateRequest.builder()
                        .name("Standard Pomodoro")
                        .workDuration(25)
                        .shortBreakDuration(5)
                        .longBreakDuration(10)
                        .pomodoroCount(4)
                        .build(),
                TimerCreateRequest.builder()
                        .name("Short Focus")
                        .workDuration(15)
                        .shortBreakDuration(3)
                        .longBreakDuration(6)
                        .pomodoroCount(3)
                        .build(),
                TimerCreateRequest.builder()
                        .name("Long Session")
                        .workDuration(50)
                        .shortBreakDuration(10)
                        .longBreakDuration(20)
                        .pomodoroCount(2)
                        .build()
        );

        LocalDateTime now = LocalDateTime.now();
        for (TimerCreateRequest dto : presets) {
            Timer timer = timerMapper.fromTimerCreateRequest(dto);
            timer.setUser(user);
            timer.setCreatedAt(now);
            timerRepository.save(timer);
        }
    }
}
