package com.kinomania.kinomania.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Table(name = "unlogged_users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnloggedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unlogged_user_id;

    private String email;

    private String name;

    private String mobile_number;

    public UnloggedUser(String name, String email, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.mobile_number = mobileNumber;
    }
}
