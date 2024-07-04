package com.kinomania.kinomania.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CinemaIncomeDTO {
    private Long cinemaId;
    private String cinemaAddress;
    private BigDecimal income;
}
