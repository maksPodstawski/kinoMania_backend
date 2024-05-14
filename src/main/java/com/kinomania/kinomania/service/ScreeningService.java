package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Screening;
import com.kinomania.kinomania.model.ScreeningDto;
import com.kinomania.kinomania.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
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
        screening.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(screeningDto.getScreeningDate()));
        screening.setMovie(movieService.findMovieById(screeningDto.getMovieId()));
        Date date = new SimpleDateFormat("HH:mm").parse(screeningDto.getScreeningTime());
        screening.setTime(new Time(date.getTime()));
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
}
