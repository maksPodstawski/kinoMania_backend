package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ScreeningService screeningService;
    private final ReservatedSeatService reservatedSeatService;

    public void addReservation(ReservationDto reservationDto, UserPrincipal userPrincipal) {
        try {
            Reservation reservation = new Reservation();
            reservation.setScreening(screeningService.getScreeningById(reservationDto.getScreeningId()));
            reservation.setUser(userService.getUserById(userPrincipal.getUserId()));

            Reservation savedReservation = reservationRepository.saveAndFlush(reservation);

            if (savedReservation != null) {
                for(var r : reservationDto.getSeatsId())
                    saveReservationWithSeat(savedReservation.getReservation_id(), r);
            } else {
                System.err.println("Saved reservation is null");
            }
        } catch (Exception e) {
            System.err.println("Error while adding reservation: " + e.getMessage());
        }
    }

    public void saveReservationWithSeat(Long reservationId, Long seatId) {
        try {
            if (reservationId != null && seatId != null) {
                reservatedSeatService.addReservatedSeat(seatId, reservationId);
            } else {
                System.err.println("Reservation ID or Seat ID is null");
            }
        } catch (Exception e) {
            System.err.println("Error while adding reservation: " + e.getMessage());
        }
    }

}
