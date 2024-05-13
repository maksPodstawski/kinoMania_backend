package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.ReservetedSeat;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservatedSeatService {

    private final ReservatedSeatsRepository reservatedSeatsRepository;
    private final ReservationRepository reservationRepository;
    private final SeatsRepository seatsRepository;


    public void addReservatedSeat(Long seatId, Long reservationId) {
        ReservetedSeat reservetedSeat = new ReservetedSeat();
        reservetedSeat.setReservation(reservationRepository.getReferenceById(reservationId));
        reservetedSeat.setSeat(seatsRepository.getReferenceById(seatId));
        reservatedSeatsRepository.save(reservetedSeat);
    }
}
