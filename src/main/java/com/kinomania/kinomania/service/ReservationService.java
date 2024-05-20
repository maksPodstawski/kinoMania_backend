package com.kinomania.kinomania.service;

import com.google.zxing.WriterException;
import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.entity.UnloggedUser;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.model.UnLoggedUserReservationDTO;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.repository.UnLoggedUserRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ScreeningService screeningService;
    private final SeatsService seatsService;
    private final ReservatedSeatService reservatedSeatService;
    private final EmailSenderService emailSenderService;
    private final QRCodeGeneratorService qrCodeGeneratorService;
    private final UnLoggedUserRepository unLoggedUserRepository;

    @Transactional
    public void addReservation(ReservationDto reservationDto, UserPrincipal userPrincipal) throws WriterException, MessagingException, IOException {
        var reservation = new Reservation();
        reservation.setUser(userService.getUserById(userPrincipal.getUserId()));
        reservation.setScreening(screeningService.getScreeningById(reservationDto.getScreeningId()));

        reservation = reservationRepository.save(reservation);

        List<Seat> seats = new ArrayList<>();
        for (Long seatId : reservationDto.getSeatsId()) {
            var seat = seatsService.getSeatById(seatId);
            seats.add(seat);
            reservatedSeatService.addReservatedSeat(reservation, seat);
        }

        BufferedImage qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(reservation.getUuid(), 250, 250);

        emailSenderService.sendEmailWithQRCode(reservation.getUser().getEmail(), "Reservation", "Your reservation" +
                        " for movie: " + reservation.getScreening().getMovie().getTitle(),
                qrCodeImage);

    }

    @Transactional
    public void addUnLoggedUserReservation(UnLoggedUserReservationDTO reservationDto) throws WriterException, MessagingException, IOException {
        var unloggedUser = unLoggedUserRepository.save(new UnloggedUser(reservationDto.getName(), reservationDto.getEmail(), reservationDto.getMobile_number()));



        var reservation = new Reservation();
        reservation.setUnloggedUser(unloggedUser);
        reservation.setScreening(screeningService.getScreeningById(reservationDto.getScreeningId()));

        reservation = reservationRepository.save(reservation);

        List<Seat> seats = new ArrayList<>();
        for (Long seatId : reservationDto.getSeatsId()) {
            var seat = seatsService.getSeatById(seatId);
            seats.add(seat);
            reservatedSeatService.addReservatedSeat(reservation, seat);
        }

        BufferedImage qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(reservation.getUuid(), 250, 250);

        emailSenderService.sendEmailWithQRCode(reservation.getUnloggedUser().getEmail(), "Reservation", "Your reservation" +
                        " for movie: " + reservation.getScreening().getMovie().getTitle(),
                qrCodeImage);

    }

    public List<Seat> getReservatedSeats(Long screeningID) {
        return reservatedSeatService.findAllReservatedSeatsByScreeningId(screeningID);
    }


}
