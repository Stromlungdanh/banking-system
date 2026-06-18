package com.quan.banking.auth.controller;

import com.quan.banking.auth.dto.request.LoginRequest;
import com.quan.banking.auth.dto.request.RegisterRequest;
import com.quan.banking.auth.dto.response.ApiResponse;
import com.quan.banking.auth.dto.response.LoginResponse;
import com.quan.banking.auth.dto.response.RegisterResponse;
import com.quan.banking.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthHealthController {

    private final AuthService authService;

    @GetMapping("/health")
    public String health() {
        return "auth-service is running";
    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = authService.register(request);

        return ApiResponse.success("Đăng kí thành công", response);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);

        return ApiResponse.success("Đăng  thành công", response);
    }
}