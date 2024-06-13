package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.repository.MovieRepository;
import com.kinomania.kinomania.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final ScreeningRepository screeningRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getAllAvailableMovies() {
        return movieRepository.findAllAvailableMovies();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie findMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public void saveMovie(Movie movie) {
        movie.setIs_in_moviePool(true);
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(Long id){

        try {
            screeningRepository.deleteScreeningsByMovieId(id);


            movieRepository.deleteById(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Transactional
    public void disableMovie(Long cinemaId) {
        movieRepository.disableMovie(cinemaId);
    }

    @Transactional
    public void enableMovie(Long cinemaId) {
        movieRepository.enableMovie(cinemaId);
    }

}
