package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.model.LoginRequest;
import com.kinomania.kinomania.model.LoginResponse;
import com.kinomania.kinomania.model.RegisterRequest;
import com.kinomania.kinomania.model.RegisterResponse;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.AuthService;
import com.kinomania.kinomania.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/api/v1/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getUsername(), request.getPassword());
    }
    @CrossOrigin
    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Validated RegisterRequest request) throws MessagingException {
        return authService.attemptRegister(request.getUsername(), request.getEmail(), request.getPassword());
    }

    @GetMapping("/api/v1/checkVip")
    public boolean checkVip(@AuthenticationPrincipal UserPrincipal principal) {
        User user = userService.getUserById(principal.getUserId());
        boolean vip = user.isVip_status();
        System.out.println(vip);
        System.out.println(user.getUsername());
        return vip;
    }
}
