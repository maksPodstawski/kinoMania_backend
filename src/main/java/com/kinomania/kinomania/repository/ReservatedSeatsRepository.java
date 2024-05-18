package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.ReservetedSeat;
import com.kinomania.kinomania.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservatedSeatsRepository extends JpaRepository<ReservetedSeat, Long> {

    @Query("SELECT rs.seat FROM ReservetedSeat rs WHERE rs.reservation.screening.screening_id = :screeningID")
    List<Seat> findAllReservatedSeatsByScreeningId(@Param("screeningID") Long screeningID);
}
