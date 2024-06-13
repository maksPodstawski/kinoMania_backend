package com.kinomania.kinomania.controller;

import com.google.zxing.WriterException;
import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.PaymentStatusDTO;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.model.UnLoggedUserReservationDTO;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.ReservationService;
import com.kinomania.kinomania.service.SeatsService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final SeatsService seatsService;


    @PostMapping("/api/v1/reservation/addReservation")
    public String addReservation(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ReservationDto reservationDto) throws MessagingException, IOException, WriterException {
        reservationService.addReservation(reservationDto, principal);
        return "Reservation added successfully by " + principal.getUsername() + " ID: " + principal.getUserId() + " for movie: "
                + reservationDto.getScreeningId() + " seat: " + reservationDto.getSeatsId();
    }

    @PostMapping("/api/v1/reservation/addReservationWithPayment")
    public PaymentStatusDTO addReservationWithPayment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ReservationDto reservationDto) throws MessagingException, IOException, WriterException {
        return reservationService.addReservationWithPayment(reservationDto, principal);
    }

    @GetMapping("/api/v1/seats/{roomID}")
    @ResponseBody
    public List<Seat> getSeats(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long roomID){
        return seatsService.getSeatsByRoomId(roomID);
    }

    @GetMapping("/api/v1/reservatedSeats/{screeningID}")
    @ResponseBody
    public List<Seat> getReservatedSeats(@PathVariable Long screeningID){
        return reservationService.getReservatedSeats(screeningID);
    }

    @PostMapping("/api/v1/reservation/addUnLoggedUserReservation")
    public String addUnLoggedUserReservation(@RequestBody UnLoggedUserReservationDTO unLoggedUserReservationDTO) throws MessagingException, IOException, WriterException {
        Reservation reservation = reservationService.addUnLoggedUserReservation(unLoggedUserReservationDTO);
        return "Reservation added successfully for movie: "
                + unLoggedUserReservationDTO.getScreeningId();
    }

    @PostMapping("/api/v1/reservation/addUnLoggedUserReservationWithPayment")
    public PaymentStatusDTO addUnLoggedUserReservationWithPayment(@RequestBody UnLoggedUserReservationDTO unLoggedUserReservationDTO) throws MessagingException, IOException, WriterException {
        return reservationService.addUnLoggedUserReservationWithPayment(unLoggedUserReservationDTO);
    }

}
