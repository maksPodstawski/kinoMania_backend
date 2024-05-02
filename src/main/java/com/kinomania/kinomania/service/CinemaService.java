package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.repository.CinemaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kinomania.kinomania.service.CinemaService;

@Service
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    public Cinema getCinemaById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cinema not found"));
    }
}
