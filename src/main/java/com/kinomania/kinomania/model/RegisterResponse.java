package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private final String accessToken;
    private final String status;
}
