package com.loveable.billingservice.service;

import com.loveable.billingservice.dto.BillingResponse;
import com.loveable.billingservice.dto.FundWalletDto;

import java.math.BigDecimal;

public interface BillingService {
//    BillingResponse fundWallet(Long id, BigDecimal amount);
    void fundWallet(FundWalletDto fundWalletDto);

    BillingResponse getBilling(Long id);
}
