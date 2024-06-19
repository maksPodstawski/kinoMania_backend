package com.kinomania.kinomania.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CinemaTicketsDTO {
    private Long cinemaId;
    private Long ticketsCount;
}
