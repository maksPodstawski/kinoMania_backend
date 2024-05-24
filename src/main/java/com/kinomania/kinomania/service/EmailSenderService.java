package com.kinomania.kinomania.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;


    @Async
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        message.setFrom(new InternetAddress("kinomania2137@gmail.com"));
        helper.setTo(toEmail);
        helper.setSubject(subject);
        String htmlMsg = "<html><body>"
                + "<h2 style=\"color: #007bff;\">Welcome to Kinomania, " + body + "!</h2>"
                + "<p style=\"font-size: 16px;\">We are thrilled to have you on board.</p>"
                + "<p style=\"font-size: 16px;\">Best regards,<br>Kinomania Team</p>"
                + "</body></html>";
        helper.setText(htmlMsg, true); // true indicates html
        mailSender.send(message);

        System.out.println("Email sent to: " + toEmail + " with subject: " + subject + " and body: " + body);
    }

    @Async
    public void sendEmailWithQRCode(String toEmail, String subject, String body, BufferedImage qrCodeImage) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "png", baos);
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        helper.addAttachment("qrcode.png", resource);

        mailSender.send(message);
    }
}
