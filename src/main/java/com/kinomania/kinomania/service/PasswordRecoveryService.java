package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.PasswordRecovery;
import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.repository.PasswordRecoveryRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final EmailSenderService emailSenderService;
    private final PasswordRecoveryRepository passwordRecoveryRepository;

    @Value("${base.url}")
    private String baseUrl;

    public void sendRecoveryEmail(User user) throws MessagingException {
        final String recoveryCode = generateRecoveryCode();

        PasswordRecovery passwordRecovery = PasswordRecovery.builder()
                .user(user)
                .recovery_code(recoveryCode)
                .recovery_date(LocalDateTime.now().plusMinutes(10))
                .build();

        passwordRecoveryRepository.save(passwordRecovery);

        String link = baseUrl+ "/passwordRecovery/" + recoveryCode;
        emailSenderService.sendEmail(user.getEmail(), "Password recovery", "Your recovery link is: " + link);
    }

    public String generateRecoveryCode() {
        final int LENGTH = 8;
        return RandomStringUtils.random(LENGTH, true, true);
    }

    public PasswordRecovery getPasswordRecoveryByCode(String recoveryCode) {
        return passwordRecoveryRepository.findByRecoveryCode(recoveryCode);
    }

    public void SetPasswordRecoveryUsed(PasswordRecovery passwordRecovery) {
        passwordRecovery.set_used(true);
        passwordRecoveryRepository.save(passwordRecovery);
    }
}
