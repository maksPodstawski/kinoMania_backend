package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.MovieService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

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

    @PostMapping("/api/v1/admin/addMovie")
    public String addMovie(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return "Movie added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @GetMapping("/api/v1/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "admin" + principal.getUsername() + " has been logged in " + principal.getUserId();
    }

}
