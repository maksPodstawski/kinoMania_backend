package com.kinomania.kinomania.service;

import com.google.zxing.WriterException;
import com.kinomania.kinomania.entity.Reservation;
import com.kinomania.kinomania.entity.Seat;
import com.kinomania.kinomania.entity.UnloggedUser;
import com.kinomania.kinomania.model.PaymentStatusDTO;
import com.kinomania.kinomania.model.ReservationDto;
import com.kinomania.kinomania.model.UnLoggedUserReservationDTO;
import com.kinomania.kinomania.repository.ReservationRepository;
import com.kinomania.kinomania.repository.UnLoggedUserRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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

    @Autowired
    private final PaypalService paypalService;

    @Transactional
    public void cancelReservation(String uuid) {
        reservationRepository.cancelReservation(uuid);
        reservatedSeatService.deleteReservatedSeatsByReservationUUID(uuid);
    }


    @Transactional
    public PaymentStatusDTO addReservationWithPayment(ReservationDto reservationDto, UserPrincipal userPrincipal) throws MessagingException, IOException, WriterException {
        Double sum = reservationDto.getSeatsId().size() * screeningService.getScreeningById(reservationDto.getScreeningId()).getPrice().doubleValue();

        try {
            var reservation = addReservation(reservationDto, userPrincipal);

            Payment payment = paypalService.createPayment(
                    sum,
                    "PLN",
                    "paypal",
                    "sale",
                    "Payment for reservation",
                    "http://localhost:5173/payment/cancel?uuid=" + reservation.getUuid(),
                    "http://localhost:5173/payment/success");

            reservationRepository.addPaymentId(reservation.getUuid(), payment.getId());

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    var paymentStatusDTO = new PaymentStatusDTO("created");
                    paymentStatusDTO.setUrl(link.getHref());
                    return paymentStatusDTO;
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new PaymentStatusDTO("Error occurred: " + e.getMessage());
        }
        return new PaymentStatusDTO("Payment creation failed.");
    }

    @Transactional
    public PaymentStatusDTO addUnLoggedUserReservationWithPayment(UnLoggedUserReservationDTO reservationDto) throws MessagingException, IOException, WriterException {
        Double sum = reservationDto.getSeatsId().size() * screeningService.getScreeningById(reservationDto.getScreeningId()).getPrice().doubleValue();

        try {
            var reservation = addUnLoggedUserReservation(reservationDto);
            Payment payment = paypalService.createPayment(
                    sum,
                    "PLN",
                    "paypal",
                    "sale",
                    "Payment for reservation",
                    "http://localhost:5173/payment/cancel?uuid=" + reservation.getUuid(),
                    "http://localhost:5173/payment/success");

            reservationRepository.addPaymentId(reservation.getUuid(), payment.getId());

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    var paymentStatusDTO = new PaymentStatusDTO("created");
                    paymentStatusDTO.setUrl(link.getHref());
                    return paymentStatusDTO;
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new PaymentStatusDTO("Error occurred: " + e.getMessage());
        }
        return new PaymentStatusDTO("Payment creation failed.");
    }



    @Transactional
    public Reservation addReservation(ReservationDto reservationDto, UserPrincipal userPrincipal) throws WriterException, MessagingException, IOException {
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

        return reservation;
    }

    @Transactional
    public Reservation addUnLoggedUserReservation(UnLoggedUserReservationDTO reservationDto) throws WriterException, MessagingException, IOException {
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


        return reservation;
    }

    public List<Seat> getReservatedSeats(Long screeningID) {
        return reservatedSeatService.findAllReservatedSeatsByScreeningId(screeningID);
    }

    @Transactional
    public void updatePaymentStatus(String paymentId) {
        reservationRepository.updatePayment(paymentId);
    }

    public Reservation getReservationByUuid(String uuid) {
        return reservationRepository.findByUuid(uuid);
    }
}
