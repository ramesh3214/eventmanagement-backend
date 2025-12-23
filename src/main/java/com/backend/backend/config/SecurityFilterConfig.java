package com.backend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔥 THIS LINE FIXES 403 ON POST
            .csrf(csrf -> csrf.disable())

            // 🔥 Disable default login page & basic auth
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // 🔥 Allow signup without auth
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/signup", "/health").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
