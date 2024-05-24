package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.model.ScreeningDto;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.ScreeningService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@AllArgsConstructor
@RestController
public class WorkerController {

    private final ScreeningService screeningService;

    @PostMapping("/api/v1/worker/addScreening")
    public String addScreening(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ScreeningDto screeningDto) throws ParseException {
        screeningService.save(screeningDto);
        return "Screening added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }
}
