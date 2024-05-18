package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.ReservetedSeat;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservatedSeatService {

    private final ReservatedSeatsRepository reservatedSeatsRepository;

    public void addReservatedSeat(Reservation reservation, Seat seat) {
        var reservatedSeat = new ReservetedSeat();
        reservatedSeat.setReservation(reservation);
        reservatedSeat.setSeat(seat);
        reservatedSeatsRepository.save(reservatedSeat);
    }

    public List<Seat> findAllReservatedSeatsByScreeningId(Long screeningID) {
        return reservatedSeatsRepository.findAllReservatedSeatsByScreeningId(screeningID);
    }
}
