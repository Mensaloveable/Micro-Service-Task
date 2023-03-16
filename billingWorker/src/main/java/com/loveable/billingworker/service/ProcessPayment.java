package com.loveable.billingworker.service;

import com.loveable.billingworker.dto.BillingResponse;

public interface ProcessPayment {
    BillingResponse processPayment(BillingResponse response);
}
