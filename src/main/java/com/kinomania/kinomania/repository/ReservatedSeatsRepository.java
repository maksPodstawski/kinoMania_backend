package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.ReservetedSeat;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.ScreeningTicketsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservatedSeatsRepository extends JpaRepository<ReservetedSeat, Long> {

    @Query("SELECT rs.seat FROM ReservetedSeat rs WHERE rs.reservation.screening.screening_id = :screeningID")
    List<Seat> findAllReservatedSeatsByScreeningId(@Param("screeningID") Long screeningID);

    @Query("SELECT s.screening_id, COUNT(rs.resevated_seat_id) AS purchasedTicketsCount " +
            "FROM Screening s " +
            "JOIN s.room r " +
            "JOIN r.cinema c " +
            "JOIN Reservation res ON s.screening_id = res.screening.screening_id " +
            "JOIN ReservetedSeat rs ON res.reservation_id = rs.reservation.reservation_id " +
            "WHERE c.cinema_id = :cinemaId " +
            "GROUP BY s.screening_id")
    List<Object[]> findReservedSeatsCountPerScreening(@Param("cinemaId") Long cinemaId);


    @Query("SELECT res.reservation.user.user_id, COUNT(res.resevated_seat_id) " +
            "FROM ReservetedSeat res " +
            "GROUP BY res.reservation.user.user_id")
    List<Object[]> findUsersReservationsCount();
}
