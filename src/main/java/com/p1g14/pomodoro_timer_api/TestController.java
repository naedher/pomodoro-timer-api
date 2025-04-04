package com.p1g14.pomodoro_timer_api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private final TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping
    public List<TestEntity> getAllTests() {
        return testRepository.findAll();
    }

    @PostMapping
    public TestEntity createTest() {
        TestEntity test = new TestEntity();
        test.setMessage("Database connection successful!");
        return testRepository.save(test);
    }
}
