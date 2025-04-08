package com.p1g14.pomodoro_timer_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

	@GetMapping("/user")
	public String helloUser() {
		return String.format("Hello user!");
	}

	@Autowired
	private TaskRepository taskRepo;

	@GetMapping
	public List<Tasks> getAllTasks() {
		return taskRepo.findAll();
	}

	@PostMapping
	public Tasks create(@RequestBody Tasks task) {
		return taskRepo.save(task);
	}



}