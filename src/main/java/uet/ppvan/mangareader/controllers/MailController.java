package uet.ppvan.mangareader.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mail")
public class MailController {

    private final JavaMailSender mailSender;

    @GetMapping("/hello")
    public String test() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("phuclaplace@gmail.com");

        msg.setSubject("Hello");
        msg.setText("This is sample message");
        mailSender.send(msg);
        return "send";
    }
}
