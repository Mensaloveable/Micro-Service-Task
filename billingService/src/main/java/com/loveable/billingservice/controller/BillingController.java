package com.loveable.billingservice.controller;

import com.loveable.billingservice.dto.BillingResponse;
import com.loveable.billingservice.dto.FundWalletDto;
import com.loveable.billingservice.service.BillingService;
import com.loveable.billingservice.utils.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/billing")
public class BillingController {
    private final BillingService billingService;
    private RabbitTemplate template;

//    @PostMapping("/fund/{id}/{amount}")
//    public BillingResponse fund(@PathVariable("id") Long idStr, @PathVariable("amount") String amountStr) {
//        BigDecimal amount = new BigDecimal(amountStr);
//        Long id = Long.valueOf(idStr);
//        return billingService.fundWallet(id, amount);
//    }

    @PostMapping("/fund/{id}/{amount}")
    public String fund(@PathVariable("id") Long idStr, @PathVariable("amount") String amountStr) {
        BigDecimal amount = new BigDecimal(amountStr);
        Long id = Long.valueOf(idStr);
        FundWalletDto fundWalletDto = new FundWalletDto(id, amount);
        billingService.fundWallet(fundWalletDto);
        return "Processing";
    }

    @GetMapping("/{id}")
    public BillingResponse getBilling(@PathVariable Long id){
        return billingService.getBilling(id);
    }
}
