package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatsDto {
    private int Rows;
    private int Columns;

    private int RoomId;
}
