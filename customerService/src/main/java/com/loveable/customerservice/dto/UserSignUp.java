package com.loveable.customerservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserSignUp {
    private String fullName;
    private String email;
    private String password;
}
