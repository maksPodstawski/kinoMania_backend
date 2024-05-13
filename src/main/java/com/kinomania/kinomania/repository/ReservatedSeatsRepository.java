package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.ReservetedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservatedSeatsRepository extends JpaRepository<ReservetedSeat, Long> {
}
