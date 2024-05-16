package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.model.LoginRequest;
import com.kinomania.kinomania.model.LoginResponse;
import com.kinomania.kinomania.model.RegisterRequest;
import com.kinomania.kinomania.model.RegisterResponse;
import com.kinomania.kinomania.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @CrossOrigin
    @PostMapping("/api/v1/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getUsername(), request.getPassword());
    }
    @CrossOrigin
    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Validated RegisterRequest request) {
        return authService.attemptRegister(request.getUsername(), request.getEmail(), request.getPassword());
    }
}
