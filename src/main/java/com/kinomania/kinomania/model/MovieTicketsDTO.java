package com.kinomania.kinomania.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieTicketsDTO {
    private Long movieId;
    private String movieTitle;
    private Long ticketsCount;
}
