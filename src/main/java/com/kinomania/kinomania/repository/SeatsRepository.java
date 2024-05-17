package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.room.room_id = :roomId")
    List<Seat> findSeatsByRoomId(@Param("roomId") Long roomId);

}
