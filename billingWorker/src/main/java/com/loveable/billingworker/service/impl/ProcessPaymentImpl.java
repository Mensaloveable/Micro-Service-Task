package com.loveable.billingworker.service.impl;

import com.loveable.billingworker.Status;
import com.loveable.billingworker.dto.BillingResponse;
import com.loveable.billingworker.service.ProcessPayment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProcessPaymentImpl implements ProcessPayment {
    @Override
    public BillingResponse processPayment(BillingResponse response) {
        return BillingResponse.builder()
                .amount(response.getAmount())
                .customerId(response.getCustomerId())
                .modifiedAt(LocalDate.now())
                .status(Status.SUCCESSFUL)
                .build();
    }
}
