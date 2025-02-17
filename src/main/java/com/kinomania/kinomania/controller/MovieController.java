package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.*;
import com.kinomania.kinomania.service.CinemaService;
import com.kinomania.kinomania.service.MovieService;
import com.kinomania.kinomania.service.RoomService;
import com.kinomania.kinomania.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MovieController {

    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final CinemaService cinemaService;
    private final RoomService roomService;


    @GetMapping("/api/v1/movies")
    @ResponseBody
    public List<Movie> getMovies() {
        return movieService.getAllAvailableMovies();
    }

    @GetMapping("/api/v1/movie/{id}")
    @ResponseBody
    public Optional<Movie> getMovie(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/api/v1/screenings")
    @ResponseBody
    public List<Screening> getScreenings() {
        return screeningService.getAllScreenings();
    }

    @GetMapping("/api/v1/screening/{id}")
    @ResponseBody
    public Screening getScreening(@PathVariable Long id) {
        return screeningService.getScreeningById(id);
    }

    @GetMapping("/api/v1/getCinemas")
    @ResponseBody
    public List<Cinema> getCinemas() {
        return cinemaService.getCinemas();
    }

    @GetMapping("/api/v1/getScreening/{city}")
    @ResponseBody
    public List<Screening> getScreeningByCity(@PathVariable String city) {
        return screeningService.getScreeningByCity(city);
    }

    @GetMapping("/api/v1/getRooms/{cinemaId}")
    @ResponseBody
    public List<Room> getRooms(@PathVariable Long cinemaId) {
        return roomService.getRoomsByCinemaId(cinemaId);
    }

}
