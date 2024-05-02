package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScreeningDto {
    private Long cinemaId;
    private Long movieId;
    private Long roomId;
    private String screeningDate;
    private String screeningTime;
    private double price;
}
