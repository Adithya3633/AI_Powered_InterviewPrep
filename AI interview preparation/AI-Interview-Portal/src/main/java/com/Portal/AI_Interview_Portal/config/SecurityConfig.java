package com.Portal.AI_Interview_Portal.config;

import com.Portal.AI_Interview_Portal.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/register", "/css/**", "/js/**").permitAll()  // Allow public access to register, login, and static resources
                .anyRequest().authenticated()  // All other pages require authentication
            )
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page URL
                .defaultSuccessUrl("/dashboard", true)  // Redirect to dashboard on successful login
                .permitAll()  // Allow all users to access the login page
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Default logout URL
                .logoutSuccessUrl("/")  // Redirect to the index page after successful logout
                .permitAll()  // Allow all users to access the logout URL
            );

        return http.build();
    }

    // Password encoder bean for hashing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication manager required for user authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
