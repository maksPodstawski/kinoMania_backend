package com.kinomania.kinomania.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentStatusDTO {
    private String status;
    private String url;

    public PaymentStatusDTO(String status) {
        this.status = status;
    }

}
