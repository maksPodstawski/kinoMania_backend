package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.model.*;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.repository.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<UserTicketsDTO> getUsersTicketsAmount( ) {
        List<Object[]> results = reservatedSeatsRepository.findUsersReservationsCount();
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
