package com.p1g14.pomodoro_timer_api.user;

import com.p1g14.pomodoro_timer_api.timer.Timer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represents a user in the system.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Timer> timers;

    /**
     * Returns the authorities granted to the user.
     * Currently, all users are assigned the USER role.
     * @return a collection of granted authorities for the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.name())); // If more roles are needed, create a role property and save to the user.
    }

    @Override
    public String getUsername() {
        return email;
    }
}
