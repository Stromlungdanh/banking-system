package com.quan.banking.auth.security;

import com.quan.banking.auth.entity.Users;
import com.quan.banking.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .disabled(users.isDeleted() || !"ACTIVE".equals(users.getStatus()))
                .authorities(
                        users.getRoles()
                                .stream()
                                .map(roles -> new SimpleGrantedAuthority(roles.getCode()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
