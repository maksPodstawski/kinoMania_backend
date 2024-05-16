package com.kinomania.kinomania.service;


import com.kinomania.kinomania.entity.Room;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.SeatsDto;
import com.kinomania.kinomania.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsService {

    public final SeatsRepository seatsRepository;
    public final RoomService roomService;


    public void saveAllSeats(SeatsDto seatsDTO) {
        Room room = roomService.getRoomById((long) seatsDTO.getRoomId());
            for(int i= 0; i < seatsDTO.getRows(); i++) {
                for(int j = 0; j < seatsDTO.getColumns(); j++) {
                    Seat seat = new Seat();
                    seat.setRoom(room);
                    seat.setSeat_row(i);
                    seat.setSeat_column(j);
                    seatsRepository.save(seat);
                }
            }
    }

    public List<Seat> getSeatsByRoomId(Long roomID){
        return seatsRepository.findSeatsByRoomId(roomID);
    }
}
