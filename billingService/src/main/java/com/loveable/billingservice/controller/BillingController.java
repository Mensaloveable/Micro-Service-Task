package com.loveable.billingservice.controller;

import com.loveable.billingservice.dto.BillingResponse;
import com.loveable.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/billing")
public class BillingController {
    private final BillingService billingService;

    @PostMapping("/fund/{id}/{amount}")
    public BillingResponse fund(@PathVariable("id") Long idStr, @PathVariable("amount") String amountStr) {
        BigDecimal amount = new BigDecimal(amountStr);
        Long id = Long.valueOf(idStr);
        return billingService.fundWallet(id, amount);
    }

    @GetMapping("/{id}")
    public BillingResponse getBilling(@PathVariable Long id){
        return billingService.getBilling(id);
    }
}
