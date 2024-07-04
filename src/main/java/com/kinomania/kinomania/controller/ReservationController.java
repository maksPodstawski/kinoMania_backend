package com.kinomania.kinomania.controller;

import com.google.zxing.WriterException;
import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.PaymentStatusDTO;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.model.ResponseDTO;
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
    public ResponseDTO addReservation(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ReservationDto reservationDto) throws MessagingException, IOException, WriterException {
        if (reservationDto.getSeatsId().isEmpty() || reservationDto.getScreeningId() == null) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        reservationService.addReservation(reservationDto, principal);
        return new ResponseDTO(1L, "Reservation added successfully");
    }

    @PostMapping("/api/v1/reservation/addReservationWithPayment")
    public PaymentStatusDTO addReservationWithPayment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ReservationDto reservationDto) throws MessagingException, IOException, WriterException {
        return reservationService.addReservationWithPayment(reservationDto, principal);
    }

    @GetMapping("/api/v1/seats/{roomID}")
    @ResponseBody
    public List<Seat> getSeats(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long roomID) {
        return seatsService.getSeatsByRoomId(roomID);
    }

    @GetMapping("/api/v1/reservatedSeats/{screeningID}")
    @ResponseBody
    public List<Seat> getReservatedSeats(@PathVariable Long screeningID) {
        return reservationService.getReservatedSeats(screeningID);
    }

    @PostMapping("/api/v1/reservation/addUnLoggedUserReservation")
    public ResponseDTO addUnLoggedUserReservation(@RequestBody UnLoggedUserReservationDTO unLoggedUserReservationDTO) throws MessagingException, IOException, WriterException {
        if (unLoggedUserReservationDTO.getSeatsId().isEmpty() || unLoggedUserReservationDTO.getScreeningId() == null || unLoggedUserReservationDTO.getName().trim().isEmpty() ||
                unLoggedUserReservationDTO.getMobile_number().trim().isEmpty() || unLoggedUserReservationDTO.getEmail().trim().isEmpty()) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        Reservation reservation = reservationService.addUnLoggedUserReservation(unLoggedUserReservationDTO);
        return new ResponseDTO(1L, "Reservation added successfully");
    }

    @PostMapping("/api/v1/reservation/addUnLoggedUserReservationWithPayment")
    public PaymentStatusDTO addUnLoggedUserReservationWithPayment(@RequestBody UnLoggedUserReservationDTO unLoggedUserReservationDTO) throws MessagingException, IOException, WriterException {
        return reservationService.addUnLoggedUserReservationWithPayment(unLoggedUserReservationDTO);
    }

}
