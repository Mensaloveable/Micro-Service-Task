package com.loveable.customerservice.service.impl;

import com.loveable.customerservice.config.JwtService;
import com.loveable.customerservice.dto.LoginResponse;
import com.loveable.customerservice.dto.UserLogin;
import com.loveable.customerservice.dto.UserSignUp;
import com.loveable.customerservice.model.User;
import com.loveable.customerservice.repository.UserRepository;
import com.loveable.customerservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public String signup (UserSignUp request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .accountBalance(BigDecimal.valueOf(0))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);

        return "Registration Successful";
    }

    @Override
    public LoginResponse login (UserLogin request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(RuntimeException::new);

        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .accountBalance(user.getAccountBalance())
                .token(jwtToken)
                .build();
    }
}
