package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name="screening_id")
    private Screening screening;

    @Column
    private int reservedSeats;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
