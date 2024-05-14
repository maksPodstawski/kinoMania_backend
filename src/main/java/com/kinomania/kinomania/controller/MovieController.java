package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.entity.Screening;
import com.kinomania.kinomania.service.CinemaService;
import com.kinomania.kinomania.service.MovieService;
import com.kinomania.kinomania.service.ScreeningService;
import com.kinomania.kinomania.entity.Cinema;
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


    @GetMapping("/api/v1/movies")
    @ResponseBody
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
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


}
