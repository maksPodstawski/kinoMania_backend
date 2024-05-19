// RoomController.java

package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Room;
import com.kinomania.kinomania.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/api/v1/getRoomsByCinema/{cinemaId}")
    @ResponseBody
    public List<Room> getRoomsByCinema(@PathVariable Long cinemaId) {
        return roomService.getRoomsByCinema(cinemaId);
    }
}
