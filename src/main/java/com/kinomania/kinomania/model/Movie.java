package com.kinomania.kinomania.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_id;

    @Column
    private String title;

    @Column
    private String director;

    @Column
    private String genre;

    @Column
    private String description;

    @Column
    private int duration;

    @Column
    private String  img_url;

}
