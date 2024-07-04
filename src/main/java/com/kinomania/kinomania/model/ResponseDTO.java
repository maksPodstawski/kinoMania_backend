package com.kinomania.kinomania.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDTO {
    private Long StatusCode;
    private String Message;
}
