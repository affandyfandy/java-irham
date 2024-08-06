package com.fpt.MidtermG1.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RevenueReportDTO {
    private LocalDate date;
    private BigDecimal revenue = BigDecimal.ZERO;
}