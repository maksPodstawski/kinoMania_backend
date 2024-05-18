package com.kinomania.kinomania.service;

import com.kinomania.kinomania.model.ScreeningTicketsDTO;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReservatedSeatsRepository reservatedSeatsRepository;

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
}
