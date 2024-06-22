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
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String director;

    @Column
    @NotBlank
    private String genre;

    @Column
    @NotBlank
    private String description;

    @Column
    @NotBlank
    private int duration;

    @Column
    @NotBlank
    private String  img_url;

    @Column
    private Boolean is_in_moviePool;

}
