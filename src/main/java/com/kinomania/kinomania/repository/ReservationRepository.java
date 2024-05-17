package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r.reservedSeats FROM Reservation r WHERE r.screening.screening_id = :id")
    List<Seat> findAllReservatedSeatsByScreeningId(@Param("id") Long screeningID);
}
