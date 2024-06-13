package com.kinomania.kinomania.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UnLoggedUserReservationDTO {
    private String email;
    private String name;
    private String mobile_number;

    private Long screeningId;
    private List<Long> seatsId;

}
