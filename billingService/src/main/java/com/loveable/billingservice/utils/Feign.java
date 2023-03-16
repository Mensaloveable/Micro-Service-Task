package com.loveable.billingservice.utils;

import com.loveable.billingservice.dto.BillingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="billingWorker", url="http://localhost:8082/api/v1/process")
public interface Feign {
    @PostMapping()
    BillingResponse processPayment(@RequestBody BillingResponse response);
}
