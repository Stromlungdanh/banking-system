package com.quan.banking.auth.security;

import com.quan.banking.auth.entity.Permission;
import com.quan.banking.auth.entity.Roles;
import com.quan.banking.auth.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-ms}")
    private Long accessTokenExpirationMs;

    @Value("${jwt.refresh-token-expiration-ms}")
    private Long refreshTokenExpirationMs;

    public String generateAccessToken(Users user) {
        return generateToken(user, accessTokenExpirationMs, "ACCESS_TOKEN");
    }

    public String generateRefreshToken(Users user) {
        return generateToken(user, refreshTokenExpirationMs, "REFRESH_TOKEN");
    }

    private String generateToken(Users user, Long expirationMs, String tokenType) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + expirationMs);

        List<String> roles = user.getRoles()
                .stream()
                .map(Roles::getCode)
                .distinct()
                .toList();

        List<String> permissions = user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getCode)
                .distinct()
                .toList();

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("tokenType", tokenType)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        return extractAllClaims(token).get("tokenType", String.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();

        return expiration.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}