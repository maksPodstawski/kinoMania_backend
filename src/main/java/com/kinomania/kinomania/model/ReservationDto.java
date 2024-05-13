package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDto {
    private Long screeningId;
    private Long seatId;
}
