package com.kinomania.kinomania.security;

import com.kinomania.kinomania.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        //.requestMatchers("/api/v1/**").permitAll()
                        .requestMatchers("/api/v1/movies").permitAll()
                        .requestMatchers("/api/v1/movie/**").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/getCinemas").permitAll()
                        .requestMatchers("/api/v1/getScreening/{city}").permitAll()
                        .requestMatchers("/api/v1/movie/**").permitAll()
                        .requestMatchers("/api/v1/seats/**").permitAll()
                        .requestMatchers("/api/v1/screening/**").permitAll()
                        .requestMatchers("/api/v1/reservation/addReservation").permitAll()
                        .requestMatchers("/api/v1/getRooms/**").permitAll()
                        .requestMatchers("/api/v1/reservatedSeats/**").permitAll()
                        .requestMatchers("/api/v1/payment/success").permitAll()
                        .requestMatchers("/api/v1/reservation/addUnLoggedUserReservation").permitAll()
                        .requestMatchers("/api/v1/panel").hasRole("ADMIN")
                        .requestMatchers("/api/v1/panel/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/panel/removeMovie/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/addCinema").hasRole("ADMIN")
                        .requestMatchers("/api/v1/worker/addScreening").hasRole("WORKER")
                        .requestMatchers("/api/v1/payment/**").permitAll()
                        .requestMatchers("/api/v1/getRoomsByWorker").permitAll()
                        .requestMatchers("/api/v1/reservation/addUnLoggedUserReservationWithPayment").permitAll()
                        .anyRequest().authenticated()
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}
