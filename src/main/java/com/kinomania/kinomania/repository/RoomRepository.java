package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query("SELECT r FROM Room r WHERE r.cinema.cinema_id = :cinemaId")
    List<Room> findRoomByCinemaId(Long cinemaId);
}
