package com.p1g14.pomodoro_timer_api.auth;

import com.p1g14.pomodoro_timer_api.auth.dto.LoginRequest;
import com.p1g14.pomodoro_timer_api.auth.dto.AuthResponse;
import com.p1g14.pomodoro_timer_api.auth.dto.RegisterRequest;
import com.p1g14.pomodoro_timer_api.config.JwtService;

import com.p1g14.pomodoro_timer_api.exception.EmailAlreadyExistsException;

import com.p1g14.pomodoro_timer_api.preferences.Preference;
import com.p1g14.pomodoro_timer_api.preferences.PreferenceRepository;
import com.p1g14.pomodoro_timer_api.preferences.PreferencesMapper;
import com.p1g14.pomodoro_timer_api.preferences.dto.PreferenceUpdateRequest;
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

/**
 * Service class for managing User-related business logic.
 */
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TimerRepository timerRepository;
    private final TimerMapper timerMapper;
    private final PreferenceRepository preferenceRepository;

    /**
     * Register a new user
     * @param request the credentials of the user
     * @return the JWT token used to authenticate the user
     * @throws RuntimeException if a user with email already exists
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("User with email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Preference preference = new Preference();
        preference.setUser(user);
        setDefaultPreferences(preference);
        preferenceRepository.save(preference);
        
        setDefaultTimers(user);

        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    /**
     * Log in using a users credentials
     * @param request the credentials of the user
     * @return the JWT token used to authenticate the user
     */
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
    }

    /**
     * Create default timers for a user
     * @param user the user to create timers for
     */

    // it should be in another clas
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

    // it should be in another clas
    public void setDefaultPreferences(Preference preference) {
        preference.setMute(false);
        preference.setAlarmSound("alarm1.wav");
        preference.setTheme("light");
    }
}
