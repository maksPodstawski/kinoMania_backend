package com.kinomania.kinomania.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column
    @JsonIgnore
    @NotBlank
    private String password;

    @Column
    private boolean vip_status;

    @Column
    @NotBlank
    private String role;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime last_login_at;
}
