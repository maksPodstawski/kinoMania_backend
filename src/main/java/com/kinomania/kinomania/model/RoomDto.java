package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class RoomDto {
    private Long cinemaId;
    private int roomNumber;
    private int numberOfSeats;
}
