package com.htwberlin.azebe.service;

import com.htwberlin.azebe.model.Shift;
import com.htwberlin.azebe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${setup.gmail.to}")
    private String setTo;

    public void sendBegin(User user, Shift shift) {
        String line = "\n***********************************\n";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("keremalitopal@gmail.com");
        message.setTo(setTo);
        message.setSubject("Beginn der werktäglichen Zeit");
        String text = line + user.getName() + "\n" + "Beginn: " + shift.getBegin().toString() + line;
        message.setText(text);
        emailSender.send(message);
    }

    public void sendEnd(User user, Shift shift) {
        String line = "\n***********************************\n";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("keremalitopal@gmail.com");
        message.setTo(setTo);
        message.setSubject("Ende der werktäglichen Zeit");
        String textBegin = line + user.getName() + "\n" + "Beginn: " + shift.getBegin().toString() + line;
        String textEnd = line + user.getName() + "\n" + "Ende: " + shift.getEnd().toString() + line;
        message.setText(textBegin + "\n" + textEnd);
        emailSender.send(message);
    }
}
