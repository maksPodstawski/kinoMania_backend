package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "password_recovery")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordRecovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long password_recovery_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "recovery_code")
    private String recovery_code;

    @Column(name = "recovery_date")
    private LocalDateTime recovery_date;

    @Column(name = "is_used")
    private boolean is_used;
}
