package com.quan.banking.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_ALREADY_EXISTS(
            "AUTH_001",
            "Tên đăng nhập đã tồn tại",
            HttpStatus.BAD_REQUEST
    ),

    EMAIL_ALREADY_EXISTS(
            "AUTH_002",
            "Email đã tồn tại",
            HttpStatus.BAD_REQUEST
    ),

    DEFAULT_ROLE_NOT_FOUND(
            "AUTH_003",
            "Không tìm thấy quyền mặc định của người dùng",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    USER_NOT_FOUND(
            "AUTH_004",
            "Không tìm thấy người dùng",
            HttpStatus.NOT_FOUND
    ),

    INVALID_USERNAME_OR_PASSWORD(
            "AUTH_005",
            "Tên đăng nhập hoặc mật khẩu không đúng",
            HttpStatus.UNAUTHORIZED
    ),

    UNAUTHENTICATED(
            "AUTH_006",
            "Bạn chưa đăng nhập",
            HttpStatus.UNAUTHORIZED
    ),

    ACCESS_DENIED(
            "AUTH_007",
            "Bạn không có quyền truy cập",
            HttpStatus.FORBIDDEN
    ),

    VALIDATION_ERROR(
            "COMMON_001",
            "Dữ liệu đầu vào không hợp lệ",
            HttpStatus.BAD_REQUEST
    ),

    ACCOUNT_DISABLED(
            "Auth_006",
            "Tài khoản đã bị khóa hoặc không hoạt động",
            HttpStatus.FORBIDDEN
    ),

    INTERNAL_SERVER_ERROR(
            "COMMON_999",
            "Lỗi hệ thống",
            HttpStatus.INTERNAL_SERVER_ERROR
    );


    private final String code;

    private final String message;

    private final HttpStatus httpStatus;
}