package com.kinomania.kinomania.model;


import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserReservationsDTO {
    private Long reservationId;
    private List<Seat> seats;
    private Long roomId;
}
