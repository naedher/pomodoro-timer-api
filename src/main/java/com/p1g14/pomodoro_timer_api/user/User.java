package com.p1g14.pomodoro_timer_api.user;

import com.p1g14.pomodoro_timer_api.preferences.Preferences;
import com.p1g14.pomodoro_timer_api.session.Session;
import com.p1g14.pomodoro_timer_api.timer_order.TimerOrder;
import com.p1g14.pomodoro_timer_api.timer.Timer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Preferences preferences;

    @OneToMany(mappedBy = "user")
    private Set<Session> sessions;

    @OneToMany(mappedBy = "user")
    private Set<Timer> timers;

    @OneToMany(mappedBy = "user")
    private Set<TimerOrder> timerOrders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.name())); // If more roles are needed, create a role property and save to the user.
    }

    @Override
    public String getUsername() {
        return email;
    }
}
