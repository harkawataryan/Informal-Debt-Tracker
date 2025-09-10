package com.jankydebt.dto;

import lombok.Data;

@Data
public class SendReminderRequest {
    // how far ahead to look for due dates
    private Integer daysAhead = 7; // default feels reasonable
}
