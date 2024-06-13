package com.kinomania.kinomania.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatsAndRoomDTO {
    private Long cinemaId;
    private int roomNumber;

    private int Rows;
    private int Columns;
}
