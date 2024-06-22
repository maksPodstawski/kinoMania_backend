package com.kinomania.kinomania.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @NotBlank
    private Cinema cinema;

    @Column
    @NotBlank
    private int room_number;

    @Column
    private int number_of_seats;

}
