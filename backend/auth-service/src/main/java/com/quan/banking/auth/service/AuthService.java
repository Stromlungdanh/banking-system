package com.quan.banking.auth.service;

import com.quan.banking.auth.dto.request.LoginRequest;
import com.quan.banking.auth.dto.request.RegisterRequest;
import com.quan.banking.auth.dto.response.LoginResponse;
import com.quan.banking.auth.dto.response.RegisterResponse;
import jakarta.validation.Valid;

public interface AuthService {
    RegisterResponse register(@Valid RegisterRequest request);

    LoginResponse login(@Valid LoginRequest request);
}
