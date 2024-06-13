package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "reserveted_seats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservetedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resevated_seat_id;


    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

}
