package com.jankydebt.service;

import com.jankydebt.domain.Debt;
import com.jankydebt.domain.Person;
import com.jankydebt.dto.CreateDebtRequest;
import com.jankydebt.repo.DebtRepository;
import com.jankydebt.repo.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtService {
    private final DebtRepository debts;
    private final PersonRepository people;

    public DebtService(DebtRepository debts, PersonRepository people) {
        this.debts = debts;
        this.people = people;
    }

    @Transactional
    public Debt create(CreateDebtRequest req) {
        Person from = people.findById(req.getFromId())
                .orElseThrow(() -> new IllegalArgumentException("fromId not found"));
        Person to = people.findById(req.getToId())
                .orElseThrow(() -> new IllegalArgumentException("toId not found"));
        if (from.getId().equals(to.getId())) {
            // no self-loans; I mean you *could* but why
            throw new IllegalArgumentException("fromId and toId must differ");
        }
        Debt d = new Debt();
        d.setFromPerson(from);
        d.setToPerson(to);
        d.setAmount(req.getAmount());
        d.setNote(req.getNote());
        d.setDueDate(req.getDueDate());
        return debts.save(d);
    }

    public List<Debt> getAll(Debt.Status status) {
        if (status == null) return debts.findAll();
        return debts.findByStatus(status);
    }

    @Transactional
    public Debt settle(Long id) {
        Debt d = debts.findById(id).orElseThrow(() -> new IllegalArgumentException("debt not found"));
        d.setStatus(Debt.Status.SETTLED);
        return d; // JPA dirty check does the rest
    }

    public List<Debt> dueWithin(int daysAhead) {
        LocalDate now = LocalDate.now();
        LocalDate max = now.plusDays(Math.max(0, daysAhead));
        return debts.findByDueDateBetweenAndStatus(now, max, Debt.Status.OPEN);
    }
}
