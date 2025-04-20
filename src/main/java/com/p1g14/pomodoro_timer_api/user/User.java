package com.p1g14.pomodoro_timer_api.user;

import com.p1g14.pomodoro_timer_api.timer.Timer;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Getter
    @OneToMany(mappedBy = "user")
    private Set<Timer> timers = new HashSet<>();
    /**
     * Adds a Timer to this User and sets the back-reference.
     */
    public void addTimer(Timer timer) {
        timers.add(timer);
        timer.setUser(this);
    }

    /**
     * Removes a Timer from this User and clears the back-reference.
     */
    public void removeTimer(Timer timer) {
        timers.remove(timer);
        timer.setUser(null);
    }
}
