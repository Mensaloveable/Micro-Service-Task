package com.loveable.customerservice.service;

import com.loveable.customerservice.dto.LoginResponse;
import com.loveable.customerservice.dto.UserLogin;
import com.loveable.customerservice.dto.UserSignUp;

public interface UserService {
    public String signup(UserSignUp userSignUp);

    public LoginResponse login(UserLogin userLogin);
}
