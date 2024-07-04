package com.kinomania.kinomania.entity;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinema_id;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String image_url;
}
