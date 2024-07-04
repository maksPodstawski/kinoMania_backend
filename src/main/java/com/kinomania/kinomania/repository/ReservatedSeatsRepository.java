package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.ReservetedSeat;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.ScreeningTicketsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
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
            "GROUP BY s.screening_id " +
            "ORDER BY COUNT(rs.resevated_seat_id) DESC ")
    List<Object[]> findReservedSeatsCountPerScreening(@Param("cinemaId") Long cinemaId);

    @Query("SELECT c.cinema_id, COUNT(rs.resevated_seat_id) AS purchasedTicketsCount " +
            "FROM Screening s " +
            "JOIN s.room r " +
            "JOIN r.cinema c " +
            "JOIN Reservation res ON s.screening_id = res.screening.screening_id " +
            "JOIN ReservetedSeat rs ON res.reservation_id = rs.reservation.reservation_id " +
            "WHERE s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY c.cinema_id " +
            "ORDER BY COUNT(rs.resevated_seat_id) DESC ")
    List<Object[]> findReservedSeatsCountPerCinema(
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate);




    @Query("SELECT c.cinema_id, SUM(s.price) AS totalTicketPrice " +
            "FROM Screening s " +
            "JOIN s.room r " +
            "JOIN r.cinema c " +
            "JOIN Reservation res ON s.screening_id = res.screening.screening_id " +
            "JOIN ReservetedSeat rs ON res.reservation_id = rs.reservation.reservation_id " +
            "WHERE s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY c.cinema_id " +
            "ORDER BY SUM(s.price) DESC")
    List<Object[]> findTotalTicketPricePerCinema(
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate);


    @Query("SELECT s.movie.movie_id, COUNT(rs.resevated_seat_id) AS purchasedTicketsCount " +
            "FROM Screening s " +
            "JOIN s.room r " +
            "JOIN r.cinema c " +
            "JOIN Reservation res ON s.screening_id = res.screening.screening_id " +
            "JOIN ReservetedSeat rs ON res.reservation_id = rs.reservation.reservation_id " +
            "WHERE s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY s.movie.movie_id " +
            "ORDER BY COUNT(rs.resevated_seat_id) DESC ")
    List<Object[]> findReservedSeatsCountPerMovie(
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate);


 /*   @Query("SELECT res.reservation.user.user_id, COUNT(res.resevated_seat_id) " +
            "FROM ReservetedSeat res " +
            "WHERE res.reservation.user.vip_status = false " +
            "GROUP BY res.reservation.user.user_id"+
            " ORDER BY COUNT(res.resevated_seat_id) DESC")
    List<Object[]> findUsersReservationsCount();*/

    @Query("SELECT res.user.user_id, COUNT(rs.resevated_seat_id) " +
            "FROM ReservetedSeat rs " +
            "JOIN Reservation res ON rs.reservation.reservation_id = res.reservation_id " +
            "JOIN Screening s ON res.screening.screening_id = s.screening_id " +
            "WHERE res.user.vip_status = false AND s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY res.user.user_id " +
            "ORDER BY COUNT(rs.resevated_seat_id) DESC")
    List<Object[]> findUsersReservationsCount(
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate);


    @Modifying
    @Query("DELETE FROM ReservetedSeat rs WHERE rs.reservation.uuid = :uuid")
    void deleteAllByReservationUuid(String uuid);
}
