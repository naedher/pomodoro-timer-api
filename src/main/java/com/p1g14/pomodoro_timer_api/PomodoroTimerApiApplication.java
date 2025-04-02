package com.p1g14.pomodoro_timer_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PomodoroTimerApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(PomodoroTimerApiApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return String.format("Hello world!");
	}
}