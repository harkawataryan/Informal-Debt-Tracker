package com.jankydebt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateDebtRequest {
    @NotNull
    private Long fromId;
    @NotNull
    private Long toId;
    @NotNull
    private BigDecimal amount;
    private String note;
    private LocalDate dueDate; // format: YYYY-MM-DD
}
