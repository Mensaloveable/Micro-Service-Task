package com.loveable.billingservice.service;

import com.loveable.billingservice.dto.BillingResponse;

import java.math.BigDecimal;

public interface BillingService {
    BillingResponse fundWallet(Long id, BigDecimal amount);

    BillingResponse getBilling(Long id);
}
