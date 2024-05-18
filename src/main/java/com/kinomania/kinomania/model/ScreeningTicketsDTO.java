package com.kinomania.kinomania.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScreeningTicketsDTO {
    private Long screeningId;
    private Long ticketsCount;
}
