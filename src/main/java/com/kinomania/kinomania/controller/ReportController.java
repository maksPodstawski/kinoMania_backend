package com.kinomania.kinomania.controller;


import com.kinomania.kinomania.model.ScreeningTicketsDTO;
import com.kinomania.kinomania.model.UserReservationsDTO;
import com.kinomania.kinomania.model.UserTicketsDTO;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.EmployeeService;
import com.kinomania.kinomania.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final EmployeeService employeeService;
    private final ReportService reportService;

    @GetMapping("/api/v1/report/ticketsForScreenings")
    public List<ScreeningTicketsDTO> ticketsForScreenings(@AuthenticationPrincipal UserPrincipal principal) {
        var userId = principal.getUserId();
        var cinemaId = employeeService.getCinemaByUserId(userId).getCinema_id();

        return reportService.getTicketsForScreening(cinemaId);
    }

    @GetMapping("/api/v1/report/usersTicketsAmount")
    public List<UserTicketsDTO> usersTicketsAmount(@AuthenticationPrincipal UserPrincipal principal) {
        return reportService.getUsersTicketsAmount();
    }

    @GetMapping("/api/v1/report/reservationsForUser")
    public List<UserReservationsDTO> reservationsForUser(@AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getUserId();
        return reportService.getUserReservations(userId);
    }
}
