package com.loveable.billingworker.dto;

import com.loveable.billingworker.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class BillingResponse {
    private Long customerId;
    private BigDecimal amount;
    private Status status;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}