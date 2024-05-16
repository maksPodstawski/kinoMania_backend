package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.ReservationService;
import com.kinomania.kinomania.service.SeatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final SeatsService seatsService;


    @PostMapping("/api/v1/reservation/addReservation")
    public String addReservation(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ReservationDto reservationDto) {
        reservationService.addReservation(reservationDto, principal);
        return "Reservation added successfully by " + principal.getUsername() + " ID: " + principal.getUserId() + " for movie: "
                + reservationDto.getScreeningId() + " seat: " + reservationDto.getSeatsId();
    }

    @GetMapping("/api/v1/seats/{roomID}")
    @ResponseBody
    public List<Seat> getSeats(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long roomID){
        return seatsService.getSeatsByRoomId(roomID);
    }

}
