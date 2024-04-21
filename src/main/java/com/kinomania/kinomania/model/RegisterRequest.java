package com.kinomania.kinomania.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}
