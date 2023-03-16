package com.loveable.customerservice.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginResponse {
    private String fullName;
    private String email;
    private BigDecimal accountBalance;
    private String token;
}
