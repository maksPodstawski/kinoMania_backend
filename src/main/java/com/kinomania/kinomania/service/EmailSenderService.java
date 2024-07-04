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
        String htmlMsg = "<html>"
                + "<head>"
                + "<style>"
                + "body {"
                + "  background: linear-gradient(to right, #6a11cb, #2575fc);"
                + "  color: #ffffff;"
                + "  font-family: 'Arial', sans-serif;"
                + "  display: flex;"
                + "  justify-content: center;"
                + "  align-items: center;"
                + "  height: 100vh;"
                + "  margin: 0;"
                + "  padding: 20px;"
                + "  box-sizing: border-box;"
                + "  text-align: center;"
                + "}"
                + ".welcome-container {"
                + "  background: rgba(255, 255, 255, 0.9);"
                + "  padding: 40px;"
                + "  border-radius: 15px;"
                + "  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);"
                + "  max-width: 600px;"
                + "  width: 100%;"
                + "  animation: fadeIn 1s ease-in-out;"
                + "}"
                + "h2 {"
                + "  color: #007bff;"
                + "  font-size: 2em;"
                + "  margin-bottom: 20px;"
                + "}"
                + "p {"
                + "  font-size: 1em;"
                + "  color: #333333;"
                + "  margin: 10px 0;"
                + "}"
                + ".logo {"
                + "  margin-bottom: 20px;"
                + "}"
                + ".logo img {"
                + "  max-width: 100%;"
                + "  height: auto;"
                + "}"
                + "@keyframes fadeIn {"
                + "  from {"
                + "    opacity: 0;"
                + "    transform: translateY(-20px);"
                + "  }"
                + "  to {"
                + "    opacity: 1;"
                + "    transform: translateY(0);"
                + "  }"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='welcome-container'>"
                + "<div class='logo'>"
                + "</div>"
                + "<h2>" + body + "</h2>"
                + "<p>Best regards,<br>Kinomania Team</p>"
                + "</div>"
                + "</body>"
                + "</html>";
        helper.setText(htmlMsg, true);
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
