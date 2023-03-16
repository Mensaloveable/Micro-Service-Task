package com.loveable.billingworker.controller;

import com.loveable.billingworker.dto.BillingResponse;
import com.loveable.billingworker.service.ProcessPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/process")
public class PaymentController {
    private final ProcessPayment processPayment;
    @PostMapping()
    public ResponseEntity<BillingResponse> process(@RequestBody BillingResponse response) {
        BillingResponse billingResponse = processPayment.processPayment(response);
        return new ResponseEntity<>(billingResponse, HttpStatus.OK);
    }
}
