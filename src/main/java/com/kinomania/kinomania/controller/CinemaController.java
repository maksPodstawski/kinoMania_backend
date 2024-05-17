package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping("/api/v1/addCinema")
    public void addCinema(@RequestBody Cinema cinema) {
        cinemaService.save(cinema);
    }

    @DeleteMapping("/api/v1/deleteCinema/{id}")
    public void deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
    }

    @GetMapping("/api/v1/cinemas")
    public List<Cinema> getCinemas() {
        return cinemaService.getCinemas();
    }

    @GetMapping("/api/v1/cinema/{id}")
    public Cinema getCinema(@PathVariable Long id) {
        return cinemaService.getCinemaById(id);
    }
}
