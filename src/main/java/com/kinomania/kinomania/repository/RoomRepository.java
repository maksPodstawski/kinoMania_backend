// RoomRepository.java

package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByCinema_CinemaId(Long cinemaId);
}
