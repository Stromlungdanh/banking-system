package com.quan.banking.auth.security;

import com.quan.banking.auth.entity.Permission;
import com.quan.banking.auth.entity.Roles;
import com.quan.banking.auth.entity.Users;
import com.quan.banking.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new DisabledException("Tài khoản đã bị khóa hoặc không hoạt động");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (Roles role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));

            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}