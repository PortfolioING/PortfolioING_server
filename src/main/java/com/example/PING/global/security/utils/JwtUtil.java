package com.example.PING.global.security.utils;

import com.example.PING.global.properties.JwtProperties;
import com.example.PING.global.security.token.JwtAuthenticationToken;
import com.example.PING.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());

        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(user.getUserId().toString()) // 사용자 ID만 저장
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getAccessTokenKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());

        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(user.getUserId().toString()) // 사용자 ID만 저장
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getRefreshTokenKey())
                .compact();
    }

    public Claims getAccessTokenClaims(Authentication authentication) {
        return Jwts.parserBuilder()
                .requireIssuer(jwtProperties.issuer())
                .setSigningKey(getAccessTokenKey())
                .build()
                .parseClaimsJws(((JwtAuthenticationToken) authentication).getJsonWebToken())
                .getBody();
    }

    private Key getAccessTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.accessTokenSecret().getBytes());
    }

    private Key getRefreshTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.refreshTokenSecret().getBytes());
    }
}
