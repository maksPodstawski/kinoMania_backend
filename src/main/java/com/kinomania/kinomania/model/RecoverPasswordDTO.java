package com.kinomania.kinomania.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecoverPasswordDTO {
    private String newPassword;
    private String recoveryCode;
}
