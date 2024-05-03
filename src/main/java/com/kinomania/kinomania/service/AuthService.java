package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.model.LoginResponse;
import com.kinomania.kinomania.model.RegisterResponse;
import com.kinomania.kinomania.repository.UserRepository;
import com.kinomania.kinomania.security.JwtIssuer;
import com.kinomania.kinomania.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public LoginResponse attemptLogin(String username, String password) {

        var token = generateAuthToken(username, password);

        if (token != null) {
            userService.updateLastLogin(username);
        }

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    public String generateAuthToken(String username, String password){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal();


        return jwtIssuer.issue(JwtIssuer.Request.builder()
                .userId(principal.getUserId())
                .username(principal.getUsername())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build());
    }

    public ResponseEntity<RegisterResponse> attemptRegister(String username, String email,String password) {
        if(userRepository.existsByUsername(username)){
            RegisterResponse response = RegisterResponse.builder()
                    .status("User already exists")
                    .accessToken("")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(userRepository.existsByEmail(email)){
            RegisterResponse response = RegisterResponse.builder()
                    .status("Email is taken")
                    .accessToken("")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        user.setVip_status(false);
        user.setCreated_at(LocalDateTime.now());

        userRepository.save(user);

        var token = generateAuthToken(username, password);

        RegisterResponse response = RegisterResponse.builder()
                .status("User registered")
                .accessToken(token)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
