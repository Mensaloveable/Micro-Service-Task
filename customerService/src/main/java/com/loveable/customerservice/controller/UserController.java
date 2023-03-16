package com.loveable.customerservice.controller;

import com.loveable.customerservice.dto.LoginResponse;
import com.loveable.customerservice.dto.UserLogin;
import com.loveable.customerservice.dto.UserSignUp;
import com.loveable.customerservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public String register(@RequestBody UserSignUp request) {
        log.info("New Customer Registered {}", request);
        return service.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserLogin request) {
        return ResponseEntity.ok(service.login(request));
    }
}
