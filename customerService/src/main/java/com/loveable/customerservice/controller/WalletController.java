package com.loveable.customerservice.controller;

import com.loveable.customerservice.dto.BillingResponse;
import com.loveable.customerservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/{amount}")
    public ResponseEntity<BillingResponse> fundWallet(@PathVariable("amount") String amountStr) {
        BigDecimal amount = new BigDecimal(amountStr);
        BillingResponse billingResponse = walletService.fundWallet(amount);
        return new ResponseEntity<>(billingResponse, HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(){
        String balance = walletService.getBalance();
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
