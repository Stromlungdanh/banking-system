package com.quan.banking.auth.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String status;
    private List<String> roles;
    private List<String> permissions;
}