package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDto {
    private Long cinemaId;
    private Long positionId;
    private Long userId;
    private String name;
    private String surname;
}
