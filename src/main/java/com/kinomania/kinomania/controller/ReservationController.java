package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/api/v1/reservation/addReservation")
    public String addReservation(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ReservationDto reservationDto) {
        reservationService.addReservation(reservationDto, principal);
        return "Reservation added successfully by " + principal.getUsername() + " ID: " + principal.getUserId() + " for movie: "
                + reservationDto.getScreeningId() + " seat: " + reservationDto.getSeatId();
    }
}
