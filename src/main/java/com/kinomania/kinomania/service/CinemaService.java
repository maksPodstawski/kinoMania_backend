package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.repository.CinemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    public List<Cinema> getCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema getCinemaById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cinema not found"));
    }
    public void deleteCinema(Long id) {
    cinemaRepository.deleteById(id);
}
}
