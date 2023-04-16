package com.loveable.billingservice.service.impl;

import com.loveable.billingservice.dto.BillingResponse;
import com.loveable.billingservice.dto.FundWalletDto;
import com.loveable.billingservice.entity.Billing;
import com.loveable.billingservice.enums.Status;
import com.loveable.billingservice.repository.BillingRepository;
import com.loveable.billingservice.service.BillingService;
import com.loveable.billingservice.utils.Feign;
import com.loveable.billingservice.utils.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.logging.Logger;

//@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.exchange.toCustomer}")
    private String exchangeToCustomer;
    @Value("${rabbitmq.routing.toCustomer}")
    private String customerRoutingKey;
//    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RabbitMQConfig.class);
    private final BillingRepository billingRepository;
    private final Feign feign;
    private final RabbitTemplate template;



    @Override
    public void fundWallet(FundWalletDto fundWalletDto) {

        Billing billing = Billing.builder()
                .customerId(fundWalletDto.getId())
                .amount(fundWalletDto.getAmount())
//                .createdAt(LocalDate.now())
                .status(Status.PENDING)
                .modifiedAt(null)
                .build();

        Billing initialBilling = billingRepository.save(billing);

        BillingResponse initial = BillingResponse.builder()
                .customerId(initialBilling.getCustomerId())
                .amount(initialBilling.getAmount())
//                .createdAt(initialBilling.getCreatedAt())
                .status(initialBilling.getStatus())
                .customerId(initialBilling.getCustomerId())
                .modifiedAt(initialBilling.getModifiedAt())
                .build();

        template.convertAndSend(exchange, routingKey, initial);
        System.out.println(initial.toString());
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

    @RabbitListener(queues = {"${rabbitmq.queue.consume}"})
    public void completePayment(BillingResponse response) {
        Billing recalledBilling = billingRepository.findById(response.getCustomerId()).get();
        recalledBilling.setStatus(response.getStatus());

        Billing finalBilling = billingRepository.save(recalledBilling);
        System.out.println("From worker service");
        System.out.println(finalBilling.toString());

        BillingResponse finalResponse = BillingResponse.builder()
                .customerId(finalBilling.getCustomerId())
                .amount(finalBilling.getAmount())
                .createdAt(finalBilling.getCreatedAt())
                .status(finalBilling.getStatus())
                .customerId(finalBilling.getCustomerId())
                .modifiedAt(finalBilling.getModifiedAt())
                .build();

        template.convertAndSend(exchangeToCustomer, customerRoutingKey, finalResponse);
    }
}

/*@Override
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
    }*/
