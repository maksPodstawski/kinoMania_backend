package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.PasswordRecovery;
import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.model.RecoverPasswordDTO;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.PasswordRecoveryService;
import com.kinomania.kinomania.service.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class PasswordRecoveryController {

    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/v1/passwordRecovery/{email}")
    public String passwordRecovery(@PathVariable String email) throws MessagingException {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return "User not found";
        }
        passwordRecoveryService.sendRecoveryEmail(user);

        return "Password recovery";
    }

    @PostMapping("/api/v1/updatePassword")
    public String updatePassword(@RequestBody RecoverPasswordDTO recoverPasswordDTO) {

        System.out.println(recoverPasswordDTO.getNewPassword());
        System.out.println(recoverPasswordDTO.getRecoveryCode());

        PasswordRecovery passwordRecovery = passwordRecoveryService.getPasswordRecoveryByCode(recoverPasswordDTO.getRecoveryCode());

        if(passwordRecovery == null) {
            return "Recovery code not found";
        }

        if(LocalDateTime.now().isAfter(passwordRecovery.getRecovery_date())) {
            return "Recovery code expired";
        }

        if(passwordRecovery.is_used()) {
            return "Recovery code already used";
        }

        User user = passwordRecovery.getUser();
        user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getNewPassword()));

        userService.updatePassword(user);
        passwordRecoveryService.SetPasswordRecoveryUsed(passwordRecovery);




        System.out.println(LocalDateTime.now());
        System.out.println(passwordRecovery.getRecovery_date());

        //userService.updatePassword(user);
        return "Password updated";
    }
}
