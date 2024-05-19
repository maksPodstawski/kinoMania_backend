package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.UserReservationsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r.reservation_id, s.seat_id, s.seat_column, s.seat_row, s.room.room_id " +
            "FROM Reservation r " +
            "JOIN ReservetedSeat rs on r.reservation_id = rs.reservation.reservation_id" +
            " join Seat s on rs.seat.seat_id = s.seat_id "
            + "WHERE r.user.user_id = :userId")
    List<Object[]> findAllByUserUserId(@Param("userId") Long userId);


}
