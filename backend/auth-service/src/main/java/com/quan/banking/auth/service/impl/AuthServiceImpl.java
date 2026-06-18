package com.quan.banking.auth.service.impl;

import com.quan.banking.auth.dto.request.LoginRequest;
import com.quan.banking.auth.dto.request.RegisterRequest;
import com.quan.banking.auth.dto.response.LoginResponse;
import com.quan.banking.auth.dto.response.RegisterResponse;
import com.quan.banking.auth.entity.Roles;
import com.quan.banking.auth.entity.Users;
import com.quan.banking.auth.exception.BusinessException;
import com.quan.banking.auth.exception.ErrorCode;
import com.quan.banking.auth.repository.RoleRepository;
import com.quan.banking.auth.repository.UserRepository;
import com.quan.banking.auth.security.JwtService;
import com.quan.banking.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final String ROLE_DEFAULT = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Roles roles = roleRepository.findByCode(ROLE_DEFAULT)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEFAULT_ROLE_NOT_FOUND));

        Users user = Users.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .roles(Set.of(roles))
                .status("ACTIVE")
                .build();

        Users savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .userId(savedUser.getId())
                .fullName(savedUser.getFullName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .status(savedUser.getStatus())
                .deleted(savedUser.isDeleted())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (DisabledException e) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        } catch (AuthenticationException e) {
            throw new BusinessException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }

        Users users = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USERNAME_OR_PASSWORD));

        String accessToken = jwtService.generateAccessToken(users);
        String refreshToken = jwtService.generateRefreshToken(users);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
