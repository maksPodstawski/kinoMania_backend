package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findScreeningByRoomCinemaCity(String city);
}
