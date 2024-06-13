package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationDto {
    private Long screeningId;
    private List<Long> seatsId;
}
