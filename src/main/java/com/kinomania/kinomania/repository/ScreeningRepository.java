package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findScreeningByRoomCinemaCity(String city);

    @Modifying
    @Query("DELETE FROM Screening s WHERE s.movie.movie_id = :id")
    void deleteScreeningsByMovieId(@Param("id") Long id);
}
