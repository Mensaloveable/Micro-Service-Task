package com.loveable.customerservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BillingResponse {
    private Long customerId;
    private BigDecimal amount;
    private String status;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}