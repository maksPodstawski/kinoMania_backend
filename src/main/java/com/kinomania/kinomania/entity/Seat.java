package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "seats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seat_id;

    private int seat_row;
    private int seat_column;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
