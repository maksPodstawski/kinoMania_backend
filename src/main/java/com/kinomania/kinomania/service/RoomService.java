package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Room;
import com.kinomania.kinomania.model.RoomDto;
import com.kinomania.kinomania.repository.CinemaRepository;
import com.kinomania.kinomania.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final CinemaRepository cinemaRepository;
    private final RoomRepository roomRepository;

    public void save(RoomDto roomDto) {
        Room room = new Room();
        room.setCinema(cinemaRepository.findById(roomDto.getCinemaId()).orElseThrow(() -> new RuntimeException("Cinema not found")));
        room.setRoom_number(roomDto.getRoomNumber());
        room.setNumber_of_seats(roomDto.getNumberOfSeats());
        roomRepository.save(room);
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
    }

}
