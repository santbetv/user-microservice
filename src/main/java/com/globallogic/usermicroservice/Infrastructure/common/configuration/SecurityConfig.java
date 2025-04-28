package com.globallogic.usermicroservice.Infrastructure.common.configuration;

import com.globallogic.usermicroservice.application.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Para poder usar H2 console
                )
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(
                                "/h2-console/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/api/auth/sign-up",
                                "/api/auth/login",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
