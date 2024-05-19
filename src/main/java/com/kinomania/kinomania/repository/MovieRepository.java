package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("SELECT m FROM Movie m WHERE m.is_in_moviePool = true")
    List<Movie> findAllAvailableMovies();

    @Modifying
    @Query("UPDATE Movie m SET m.is_in_moviePool = false WHERE m.movie_id = :movieId")
    void disableMovie(@Param("movieId")Long movieId);

    @Modifying
    @Query("UPDATE Movie m SET m.is_in_moviePool = true WHERE m.movie_id = :movieId")
    void enableMovie(@Param("movieId")Long movieId);
}
