package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Transactional
    @Modifying
    @Query("UPDATE Reservation r SET r.isPaid = true WHERE r.paymentId = :paymentId")
    void updatePayment(@Param("paymentId") String paymentId);

    @Query("select r from Reservation r where r.uuid = :uuid")
    Reservation findByUuid(@Param("uuid") String uuid);

    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.paymentId = :id WHERE r.uuid = :uuid")
    void addPaymentId(String uuid, String id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.uuid = :uuid")
    void deleteByUUID(String uuid);

    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.isCanceled = true WHERE r.uuid = :uuid")
    void cancelReservation(String uuid);
}
