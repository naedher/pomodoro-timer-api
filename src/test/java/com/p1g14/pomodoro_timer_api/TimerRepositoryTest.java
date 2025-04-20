

// src/test/java/com/p1g14/pomodoro_timer_api/TimerRepositoryTest.java
package com.p1g14.pomodoro_timer_api;


import com.p1g14.pomodoro_timer_api.timer.Timer;
import com.p1g14.pomodoro_timer_api.timer.TimerRepository;
import com.p1g14.pomodoro_timer_api.user.User;
import com.p1g14.pomodoro_timer_api.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
//to ensure each test runs in its own transaction and rolls back cleanly.
@Transactional
@Rollback
public class TimerRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(TimerRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimerRepository timerRepository;

    @Test
    public void createAndFetchTimer() {
        log.info("--- createAndFetchTimer test started ---");

        log.info("1) Creating a new User");
        User u = new User();
        u.setEmail("dummy@example.com");
        u.setPassword("secret");
        User savedUser = userRepository.save(u);
        log.info("   -> Saved User ID = {}", savedUser.getId());

        log.info("2) Creating a new Timer for that user");
        Timer t = new Timer();
        t.setUser(savedUser);
        t.setName("Test Timer");
        t.setCreatedAt(LocalDateTime.now());
        t.setWorkDuration(25);
        t.setBreakDuration(5);
        t.setPomodoroCount(4);
        Timer savedTimer = timerRepository.save(t);
        log.info("   -> Saved Timer ID = {}", savedTimer.getId());

        log.info("3) Verifying the ID is not null");
        assertThat(savedTimer.getId()).isNotNull();

        log.info("4) Fetching the Timer from DB and validating fields");
        Timer fetched = timerRepository.findById(savedTimer.getId()).orElseThrow();
        log.info("   -> Fetched Timer Name = {}", fetched.getName());
        log.info("   -> Fetched WorkDuration = {}", fetched.getWorkDuration());
        log.info("   -> Fetched User ID = {}", fetched.getUser().getId());

        assertThat(fetched.getName()).isEqualTo("Test Timer");
        assertThat(fetched.getWorkDuration()).isEqualTo(25);
        assertThat(fetched.getUser().getId()).isEqualTo(savedUser.getId());

        log.info("--- createAndFetchTimer test finished successfully ---");
    }

    @Test
    public void fetchAllTimersForUser() {
        log.info("--- fetchAllTimersForUser test started ---");

        log.info("1) Saving two different timers for the same user");
        User u = userRepository.save(new User(null, "foo@bar.com", "pw", null));
        Timer t1 = timerRepository.save(new Timer(null, u, "A", LocalDateTime.now(), 10, 2, 1));
        Timer t2 = timerRepository.save(new Timer(null, u, "B", LocalDateTime.now(), 15, 3, 2));
        log.info("   -> Saved timer names = {}, {}", t1.getName(), t2.getName());

        log.info("2) Retrieving list via findByUserId()");
        List<Timer> list = timerRepository.findByUserId(u.getId());
        log.info("   -> Retrieved list size = {}", list.size());
        log.info("   -> List contents = {}", list.stream().map(Timer::getName).toList());

        assertThat(list)
                .hasSize(2)
                .extracting(Timer::getName)
                .containsExactlyInAnyOrder("A", "B");

        log.info("=== fetchAllTimersForUser test finished successfully ===");
    }
}