package com.jankydebt.repo;

import com.jankydebt.domain.Debt;
import com.jankydebt.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByStatus(Debt.Status status);
    List<Debt> findByDueDateBetweenAndStatus(LocalDate from, LocalDate to, Debt.Status status);
    List<Debt> findByFromPersonAndStatus(Person from, Debt.Status status);
}
