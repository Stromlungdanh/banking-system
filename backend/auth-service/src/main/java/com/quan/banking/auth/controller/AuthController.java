package com.quan.banking.auth.controller;

import com.quan.banking.auth.dto.request.LoginRequest;
import com.quan.banking.auth.dto.request.RegisterRequest;
import com.quan.banking.auth.dto.response.ApiResponse;
import com.quan.banking.auth.dto.response.LoginResponse;
import com.quan.banking.auth.dto.response.RegisterResponse;
import com.quan.banking.auth.dto.response.UserProfileResponse;
import com.quan.banking.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);

        return ApiResponse.success("Đăng kí thành công", response);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);

        return ApiResponse.success("Đăng nhập thành công", response);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('AUTH_ME_READ')")
    public ApiResponse<UserProfileResponse> getCurrentUser(Authentication authentication) {
        UserProfileResponse response = authService.getCurrentUser(authentication.getName());

        return ApiResponse.success("Lấy thông tin user thành công", response);
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> adminOnly() {
        return ApiResponse.success("Bạn là ADMIN", "ADMIN API OK");
    }

    @GetMapping("/permission/test")
    @PreAuthorize("hasAuthority('ADMIN_USER_READ')")
    public ApiResponse<String> permissionTest() {
        return ApiResponse.success("Bạn có quyền ADMIN_USER_READ", "PERMISSION OK");
    }
}