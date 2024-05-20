package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.model.PaymentStatusDTO;
import com.kinomania.kinomania.service.PaypalService;
import com.kinomania.kinomania.service.ReservationService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    ReservationService reservationService;

    @PostMapping("/api/v1/payment/pay")
    public PaymentStatusDTO createPayment(@RequestParam("sum") double sum) {
        try {
            Payment payment = paypalService.createPayment(
                    sum,
                    "PLN",
                    "paypal",
                    "sale",
                    "Payment for reservation",
                    "http://localhost:5171/payment/cancel",
                    "http://localhost:5171/payment/success");
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new PaymentStatusDTO(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return new PaymentStatusDTO("Error occurred: " + e.getMessage());
        }
        return new PaymentStatusDTO("Payment creation failed.");
    }

    @GetMapping("/api/v1/payment/cancel")
    public String cancelPay() {
        return "Payment cancelled";
    }

    @PostMapping ("/api/v1/payment/success")
    public PaymentStatusDTO successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws PayPalRESTException {
        Payment payment = paypalService.getPayment(paymentId, payerId);

        if (payment.getPayer() != null && payment.getPayer().getPayerInfo() != null && Objects.equals(payment.getPayer().getPayerInfo().getPayerId(), payerId)) {
            reservationService.updatePaymentStatus(paymentId);
            return new PaymentStatusDTO("success");
        }
        return new PaymentStatusDTO("failed");
    }
}
