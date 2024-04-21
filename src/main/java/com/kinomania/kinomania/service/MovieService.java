package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

}
