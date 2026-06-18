package com.quan.banking.auth.security;

import com.quan.banking.auth.entity.Roles;
import com.quan.banking.auth.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-ms}")
    private Long accessTokenExpirationMs;

    @Value("${jwt.refresh-token-expiration-ms}")
    private Long refreshTokenExpirationMs;

    public String generateAccessToken(Users users) {
        return generateToken(users, accessTokenExpirationMs, "ACCESS_TOKEN");
    }

    public String generateRefreshToken(Users users) {
        return generateToken(users, refreshTokenExpirationMs, "REFRESH_TOKEN");
    }

    private String generateToken(Users users, Long expirationMs, String tokenType) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(users.getUsername())
                .claim("userId", users.getId())
                .claim("tokenType", tokenType)
                .claim("roles", users.getRoles().stream().map(Roles::getCode).toList())
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

    public boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();

        return expiration.before(new Date());
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
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


