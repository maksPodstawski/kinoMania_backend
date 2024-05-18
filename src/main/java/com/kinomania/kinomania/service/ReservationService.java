package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ScreeningService screeningService;
    private final SeatsService seatsService;
    private final ReservatedSeatService reservatedSeatService;

    @Transactional
    public void addReservation(ReservationDto reservationDto, UserPrincipal userPrincipal) {
        var reservation = new Reservation();
        reservation.setUser(userService.getUserById(userPrincipal.getUserId()));
        reservation.setScreening(screeningService.getScreeningById(reservationDto.getScreeningId()));

        reservation = reservationRepository.save(reservation);

        List<Seat> seats = new ArrayList<>();
        for(Long seatId : reservationDto.getSeatsId()) {
            var seat = seatsService.getSeatById(seatId);
            seats.add(seat);
            reservatedSeatService.addReservatedSeat(reservation, seat);
        }


    }

    public List<Seat> getReservatedSeats(Long screeningID){
        return reservatedSeatService.findAllReservatedSeatsByScreeningId(screeningID);
    }




}
