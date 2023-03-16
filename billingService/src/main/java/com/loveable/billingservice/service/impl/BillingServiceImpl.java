package com.loveable.billingservice.service.impl;

import com.loveable.billingservice.dto.BillingResponse;
import com.loveable.billingservice.entity.Billing;
import com.loveable.billingservice.enums.Status;
import com.loveable.billingservice.repository.BillingRepository;
import com.loveable.billingservice.service.BillingService;
import com.loveable.billingservice.utils.Feign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final BillingRepository billingRepository;
    private final Feign feign;
    @Override
    public BillingResponse fundWallet(Long id, BigDecimal amount) {
        Billing billing = Billing.builder()
                .customerId(id)
                .amount(amount)
                .createdAt(LocalDate.now())
                .status(Status.PENDING)
                .modifiedAt(null)
                .build();

        Billing initialBilling = billingRepository.save(billing);

        BillingResponse initial = BillingResponse.builder()
                .customerId(initialBilling.getCustomerId())
                .amount(initialBilling.getAmount())
                .createdAt(initialBilling.getCreatedAt())
                .status(initialBilling.getStatus())
                .customerId(initialBilling.getCustomerId())
                .modifiedAt(initialBilling.getModifiedAt())
                .build();

        BillingResponse workerResponse = feign.processPayment(initial);

        Billing recalledBilling = billingRepository.findById(initialBilling.getId()).get();

        recalledBilling.setStatus(workerResponse.getStatus());
        recalledBilling.setModifiedAt(workerResponse.getModifiedAt());

        Billing finalBilling = billingRepository.save(recalledBilling);

        return BillingResponse.builder()
                .customerId(finalBilling.getCustomerId())
                .amount(finalBilling.getAmount())
                .createdAt(finalBilling.getCreatedAt())
                .status(finalBilling.getStatus())
                .customerId(finalBilling.getCustomerId())
                .modifiedAt(finalBilling.getModifiedAt())
                .build();
    }

    @Override
    public BillingResponse getBilling(Long id) {
        Billing response = billingRepository.findById(id).get();

        return BillingResponse.builder()
                .customerId(response.getCustomerId())
                .amount(response.getAmount())
                .createdAt(response.getCreatedAt())
                .status(response.getStatus())
                .customerId(response.getCustomerId())
                .modifiedAt(response.getModifiedAt())
                .build();
    }
}
