package com.jankydebt.service;

import com.jankydebt.domain.Debt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendDebtReminder(Debt d) {
        // yeah, plain text is fine for now
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(d.getFromPerson().getEmail());
        msg.setSubject("Reminder: you owe " + d.getToPerson().getName());
        String due = d.getDueDate() != null ? (" by " + d.getDueDate()) : "";
        msg.setText("Hey " + d.getFromPerson().getName() + ",\n\n" +
                "You owe " + d.getToPerson().getName() + " $" + d.getAmount() + due + ".\n" +
                (d.getNote() != null ? ("Note: " + d.getNote() + "\n") : "") +
                "Please settle when you can. Thanks!\n\n" +
                "— Informal Debt Tracker");
        mailSender.send(msg);
    }
}
