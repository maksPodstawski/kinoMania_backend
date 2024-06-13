package com.kinomania.kinomania.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatDTO2 {
    public Long seatId;
    public int row;
    public int column;
}
