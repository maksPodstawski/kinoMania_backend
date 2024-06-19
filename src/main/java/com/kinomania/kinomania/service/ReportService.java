package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.*;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReservatedSeatsRepository reservatedSeatsRepository;
    private final ReservationRepository reservationRepository;
    private final SeatsRepository seatsRepository;
    private final ScreeningService screeningService;

    public List<ScreeningTicketsDTO> getTicketsForScreening(Long cinemaId) {
        List<Object[]> results = reservatedSeatsRepository.findReservedSeatsCountPerScreening(cinemaId);
        List<ScreeningTicketsDTO> screeningTicketsDTOList = new ArrayList<>();

        for (Object[] result : results) {
            Long screeningId = (Long) result[0];
            Long purchasedTicketsCount = (Long) result[1];
            ScreeningTicketsDTO screeningTicketsDTO = new ScreeningTicketsDTO(screeningId, purchasedTicketsCount);
            screeningTicketsDTOList.add(screeningTicketsDTO);
        }

        return screeningTicketsDTOList;
    }

    public List<CinemaTicketsDTO> getTicketsPerCinema(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = reservatedSeatsRepository.findReservedSeatsCountPerCinema(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
        List<CinemaTicketsDTO> cinemaTicketsDTOList = new ArrayList<>();

        for (Object[] result : results) {
            Long cinemaId = (Long) result[0];
            Long purchasedTicketsCount = (Long) result[1];
            CinemaTicketsDTO cinemaTicketsDTO = new CinemaTicketsDTO(cinemaId, purchasedTicketsCount);
            cinemaTicketsDTOList.add(cinemaTicketsDTO);
        }

        return cinemaTicketsDTOList;
    }

    public List<CinemaIncomeDTO> getTotalTicketPricePerCinema(LocalDateTime startDate, LocalDateTime endDate){
        List<Object[]> results = reservatedSeatsRepository.findTotalTicketPricePerCinema(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
        List<CinemaIncomeDTO> cinemaIncomeDTOList = new ArrayList<>();

        for (Object[] result : results) {
            Long cinemaId = (Long) result[0];
            BigDecimal totalTicketPrice = (BigDecimal) result[1];
            CinemaIncomeDTO cinemaIncomeDTO = new CinemaIncomeDTO(cinemaId, totalTicketPrice);
            cinemaIncomeDTOList.add(cinemaIncomeDTO);
        }

        return cinemaIncomeDTOList;
    }

    public List<MovieTicketsDTO> getReservedSeatsCountPerMovie(LocalDateTime startDate, LocalDateTime endDate){
        List<Object[]> results = reservatedSeatsRepository.findReservedSeatsCountPerMovie(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
        List<MovieTicketsDTO> movieTicketsDTOList = new ArrayList<>();

        for (Object[] result : results) {
            Long movieId = (Long) result[0];
            Long purchasedTicketsCount = (Long) result[1];
            MovieTicketsDTO movieTicketsDTO = new MovieTicketsDTO(movieId, purchasedTicketsCount);
            movieTicketsDTOList.add(movieTicketsDTO);
        }

        return movieTicketsDTOList;
    }

    public List<UserTicketsDTO> getUsersTicketsAmount(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = reservatedSeatsRepository.findUsersReservationsCount(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
        List<UserTicketsDTO> userTicketsDTOList = new ArrayList<>();

        for (Object[] result : results) {
            Long userId = (Long) result[0];
            Long ticketsAmount = (Long) result[1];
            System.out.println(ticketsAmount);
            UserTicketsDTO userTicketsDTO = new UserTicketsDTO(userId, ticketsAmount);
            userTicketsDTOList.add(userTicketsDTO);
        }


        return userTicketsDTOList;
    }

    public List<UserReservationsDTO> getUserReservations(Long userId) {
        List<Object[]> results = reservationRepository.findAllByUserUserId(userId);
        Map<Long, UserReservationsDTO> reservationsMap = new HashMap<>();

        for (Object[] result : results) {
            Long reservationId = (Long) result[0];
            Long seatId = (Long) result[1];
            Integer seatColumn = (Integer) result[2];
            Integer seatRow = (Integer) result[3];
            Long roomId = (Long) result[4];

            UserReservationsDTO dto = reservationsMap.get(reservationId);
            if (dto == null) {
                dto = new UserReservationsDTO();
                dto.setReservationId(reservationId);
                dto.setSeats(new ArrayList<>());
                dto.setScreening(screeningService.getScreeningByReservationId(reservationId));
                reservationsMap.put(reservationId, dto);
            }

            Seat seat = seatsRepository.findById(seatId).orElse(null);
            SeatDTO2 seatsDto = new SeatDTO2(seatId, seatRow, seatColumn);
            if (seat != null) {
                dto.getSeats().add(seatsDto);
            }
        }

        return new ArrayList<>(reservationsMap.values());
    }
}
