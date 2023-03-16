package com.loveable.customerservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserLogin {
    private String email;
    private String password;
}
