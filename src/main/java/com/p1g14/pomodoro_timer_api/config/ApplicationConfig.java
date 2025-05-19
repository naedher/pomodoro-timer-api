package com.p1g14.pomodoro_timer_api.config;

import com.p1g14.pomodoro_timer_api.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class that defines application-wide Spring beans
 * for authentication, password encoding, and object mapping.
 */
@Configuration
public class ApplicationConfig {

    @Autowired
    private UserRepository userRepository;

    /**
     * Provides a {@link ModelMapper} bean for object-to-object mapping between DTOs and entities.
     *
     * @return a configured ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Provides a {@link UserDetailsService} bean for retrieving user details during authentication.
     *
     * @return a UserDetailsService implementation that loads users from the database
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));
    }

    /**
     * Provides an {@link AuthenticationProvider} bean used to authenticate users
     * with the configured {@link UserDetailsService} and {@link PasswordEncoder}.
     *
     * @return a configured DAOAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides an {@link AuthenticationManager} bean used by Spring Security to perform authentication.
     *
     * @param config the authentication configuration provided by Spring Boot
     * @return an AuthenticationManager instance
     * @throws Exception if the manager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides a {@link PasswordEncoder} bean used to hash and verify passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
