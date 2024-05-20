package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Screening;
import com.kinomania.kinomania.model.ScreeningDto;
import com.kinomania.kinomania.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final RoomService roomService;
    private final MovieService movieService;

    public void save(ScreeningDto screeningDto) throws ParseException {
        Screening screening = new Screening();
        screening.setRoom(roomService.getRoomById(screeningDto.getRoomId()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        screening.setDate(dateFormat.parse(screeningDto.getScreeningDate()));
        screening.setMovie(movieService.findMovieById(screeningDto.getMovieId()));
        screening.setPrice(BigDecimal.valueOf(screeningDto.getPrice()));
        screeningRepository.save(screening);
    }

    public Screening getScreeningById(Long id) {
        return screeningRepository.findById(id).orElseThrow();
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public List<Screening> getScreeningByCity(String city) {
        return screeningRepository.findScreeningByRoomCinemaCity(city);
    }

    public Screening getScreeningByReservationId(Long reservationId) {
        return screeningRepository.findScreeningByReservationId(reservationId);
    }
}
