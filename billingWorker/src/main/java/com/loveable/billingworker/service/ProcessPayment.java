package com.loveable.billingworker.service;

import com.loveable.billingworker.dto.BillingResponse;
import com.loveable.billingworker.dto.FundWalletDto;

public interface ProcessPayment {
//    BillingResponse processPayment(BillingResponse response);
void processPayment(BillingResponse response);
}
