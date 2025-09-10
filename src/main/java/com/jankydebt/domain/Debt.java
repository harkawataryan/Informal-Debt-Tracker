package com.jankydebt.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Debt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Person fromPerson; // who owes

    @ManyToOne(optional = false)
    private Person toPerson;   // who should get paid

    @NotNull
    private BigDecimal amount;

    private String note;

    private LocalDate dueDate; // can be null if "whenever"

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    public enum Status { OPEN, SETTLED }
}
