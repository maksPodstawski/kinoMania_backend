package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Table(name = "screenings")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screening_id;

    @ManyToOne
    @JoinColumn(name="movie_id")
    @NotBlank
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="room_id")
    @NotBlank
    private Room room;

    @Column
    @NotBlank
    private Date date;

    @Column
    @NotBlank
    private BigDecimal price;

}
