package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.ReservetedSeat;
import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservatedSeatService {

    private final ReservationRepository reservationRepository;


}
