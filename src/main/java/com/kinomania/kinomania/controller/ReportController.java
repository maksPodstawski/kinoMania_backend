package com.kinomania.kinomania.controller;


import com.kinomania.kinomania.model.*;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.EmployeeService;
import com.kinomania.kinomania.service.ReportService;
import com.kinomania.kinomania.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final EmployeeService employeeService;
    private final ReportService reportService;
    private final TimeService timeService;

    @GetMapping("/api/v1/report/ticketsForScreenings")
    public List<ScreeningTicketsDTO> ticketsForScreenings(@AuthenticationPrincipal UserPrincipal principal) {
        var userId = principal.getUserId();
        var cinemaId = employeeService.getCinemaByUserId(userId).getCinema_id();

        return reportService.getTicketsForScreening(cinemaId);
    }

    @GetMapping("/api/v1/report/usersTicketsAmount")
    public List<UserTicketsDTO> usersTicketsAmount(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TimeSpanDTO timeSpanDTO) {
        return reportService.getUsersTicketsAmount(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
    }

    @GetMapping("/api/v1/report/reservationsForUser")
    public List<UserReservationsDTO> reservationsForUser(@AuthenticationPrincipal UserPrincipal principal) {
        Long userId = principal.getUserId();
        return reportService.getUserReservations(userId);
    }

    @GetMapping("/api/v1/report/ticketsPerCinema")
    public List<CinemaTicketsDTO> ticketsPerCinema(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TimeSpanDTO timeSpanDTO) {
        return reportService.getTicketsPerCinema(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
    }

    @GetMapping("/api/v1/report/ticketsPerMovie")
    public List<MovieTicketsDTO> ticketsPerMovie(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TimeSpanDTO timeSpanDTO) {
        return reportService.getReservedSeatsCountPerMovie(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
    }

    @GetMapping("/api/v1/report/incomePerCinema")
    public List<CinemaIncomeDTO> incomePerCinema(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TimeSpanDTO timeSpanDTO) {
        return reportService.getTotalTicketPricePerCinema(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
    }
}
