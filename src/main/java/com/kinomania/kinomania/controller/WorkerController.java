package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.model.ScreeningDto;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.ScreeningService;
import com.kinomania.kinomania.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@AllArgsConstructor
@RestController
public class WorkerController {

    private final ScreeningService screeningService;
    private final UserService userService;

    @PostMapping("/api/v1/worker/addScreening")
    public String addScreening(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ScreeningDto screeningDto) throws ParseException {
        screeningService.save(screeningDto);
        return "Screening added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @PutMapping("/api/v1/worker/setVipStatus/{userID}")
    public String setVipStatus(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long userID) {
        userService.setVipStatus(userID);
        return "Vip status set successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }
}
