package com.loveable.billingworker.service.impl;

import com.loveable.billingworker.dto.BillingResponse;
import com.loveable.billingworker.dto.FundWalletDto;
import com.loveable.billingworker.enums.Status;
import com.loveable.billingworker.service.ProcessPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProcessPaymentImpl implements ProcessPayment {
    private final RabbitTemplate template;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    /*@Override
    public BillingResponse processPayment(BillingResponse response) {
        return BillingResponse.builder()
                .amount(response.getAmount())
                .customerId(response.getCustomerId())
                .modifiedAt(LocalDate.now())
                .status(Status.SUCCESSFUL)
                .build();
    }*/

    @Override
    @RabbitListener(queues = {"${rabbitmq.queue.consume}"})
    public void processPayment(BillingResponse response) {
        System.out.println(response);
        BillingResponse processedPayment = BillingResponse.builder()
                .amount(response.getAmount())
                .customerId(response.getCustomerId())
//                .modifiedAt(LocalDate.now())
                .status(Status.SUCCESSFUL)
                .build();
        template.convertAndSend(exchange, routingKey, processedPayment);
    }
}
