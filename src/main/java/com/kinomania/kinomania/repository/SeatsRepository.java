package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, Long> {
}
