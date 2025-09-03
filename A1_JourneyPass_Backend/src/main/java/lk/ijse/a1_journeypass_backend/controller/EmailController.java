package lk.ijse.a1_journeypass_backend.controller;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
@CrossOrigin("http://localhost:63342")
@RestController
@RequestMapping("api/v1/JourneyPass/send")
public class EmailController {
    private final JavaMailSender javaMailSender;

    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/send-email")
    public String sendEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("charithmihiranga453@gmail.com");
            message.setTo("charithmihiranga453@gmail.com");
            message.setSubject("Your QR Code.");
            message.setText("Your QR Code has been generated.");

            javaMailSender.send(message);
            return "Your Email has been send.";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("charithmihiranga453@gmail.com");
            helper.setTo("charithmihiranga453@gmail.com");
            helper.setSubject("Your QR Code.");
            helper.setText("Your QR Code has been generated.");

            helper.addAttachment("qr.pnd",new File("//Users//charithsiriwardana//DownloadsProject\\QrCode2025-04-15 16/07/30.png"));

            javaMailSender.send(message);
            return "Your Email has been send.";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

}