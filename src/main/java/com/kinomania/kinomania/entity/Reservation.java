package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name="screening_id")
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "unlogged_user_id")
    private UnloggedUser unloggedUser;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private String uuid;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "is_canceled")
    private Boolean isCanceled;


    @PrePersist
    protected void onCreate() {
        if(this.isPaid == null) {
            this.isPaid = false;
        }
        if(this.isCanceled == null) {
            this.isPaid = false;
        }
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

}