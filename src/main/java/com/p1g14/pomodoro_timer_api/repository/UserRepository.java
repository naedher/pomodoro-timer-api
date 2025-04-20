package com.p1g14.pomodoro_timer_api.repository;
// src/main/java/com/p1g14/pomodoro_timer_api/user/UserRepository.java


import com.p1g14.pomodoro_timer_api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }

