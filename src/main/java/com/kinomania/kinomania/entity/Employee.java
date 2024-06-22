package com.kinomania.kinomania.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @NotBlank
    private Cinema cinema;

    @OneToOne
    @JoinColumn(name="user_id")
    @NotBlank
    private User user;

    @ManyToOne
    @NotBlank
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "surname")
    @NotBlank
    private String surname;
}
