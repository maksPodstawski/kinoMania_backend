package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column
    private Boolean is_in_moviePool;

}
