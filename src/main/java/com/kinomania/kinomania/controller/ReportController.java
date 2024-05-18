package com.kinomania.kinomania.controller;


import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.model.ScreeningTicketsDTO;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.EmployeeService;
import com.kinomania.kinomania.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final EmployeeService employeeService;
    private final ReportService reportService;

    @GetMapping("/api/v1/report/ticketsForScreening")
    public List<ScreeningTicketsDTO> report(@AuthenticationPrincipal UserPrincipal principal) {
        var userId = principal.getUserId();
        var cinemaId = employeeService.getCinemaByUserId(userId).getCinema_id();

        return reportService.getTicketsForScreening(cinemaId);
    }
}
