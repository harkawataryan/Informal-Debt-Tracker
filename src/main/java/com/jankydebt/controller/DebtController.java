package com.jankydebt.controller;

import com.jankydebt.domain.Debt;
import com.jankydebt.dto.CreateDebtRequest;
import com.jankydebt.dto.SendReminderRequest;
import com.jankydebt.service.DebtService;
import com.jankydebt.service.MailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DebtController {
    private final DebtService debts;
    private final MailService mail;

    public DebtController(DebtService debts, MailService mail) {
        this.debts = debts;
        this.mail = mail;
    }

    @GetMapping("/debts")
    public List<Debt> list(@RequestParam(value = "status", required = false) Debt.Status status) {
        return debts.getAll(status);
    }

    @PostMapping("/debts")
    @ResponseStatus(HttpStatus.CREATED)
    public Debt create(@Valid @RequestBody CreateDebtRequest req) {
        return debts.create(req);
    }

    @PostMapping("/debts/{id}/settle")
    public Debt settle(@PathVariable Long id) {
        return debts.settle(id);
    }

    @PostMapping("/reminders/send")
    public String sendReminders(@RequestBody(required = false) SendReminderRequest req) {
        int days = (req == null || req.getDaysAhead() == null) ? 7 : req.getDaysAhead();
        var dueSoon = debts.dueWithin(days);
        int count = 0;
        for (Debt d : dueSoon) {
            try {
                mail.sendDebtReminder(d);
                count++;
            } catch (Exception e) {
                // meh, loggers exist but println is quick
                System.err.println("failed sending reminder for debt " + d.getId() + ": " + e.getMessage());
            }
        }
        return "sent " + count + " reminder(s)";
    }
}
