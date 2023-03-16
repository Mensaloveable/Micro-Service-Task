package com.loveable.customerservice.utils;

import com.loveable.customerservice.dto.BillingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@FeignClient(name="billingService", url="http://localhost:8081/api/v1/billing")
public interface Feign {
    @PostMapping("/fund/{id}/{amount}")
    BillingResponse fund(@PathVariable("id") Long id, @PathVariable("amount") BigDecimal amount);
}
